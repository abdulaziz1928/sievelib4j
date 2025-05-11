package com.abdulaziz.builder;

import com.abdulaziz.builder.actions.*;
import com.abdulaziz.builder.conditions.*;
import com.abdulaziz.builder.control.*;

import lombok.Builder;
import lombok.Getter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.abdulaziz.builder.SieveImports.Actions;
import static com.abdulaziz.builder.SieveImports.Conditions;

@Builder
public class SieveBuilder {
    private final UUID id;
    @Builder.Default
    @Getter
    private final ControlRequire imports = new ControlRequire();
    private final ControlIf ifStatement;
    @Builder.Default
    private final List<ControlElseIf> elseIfStatements = new ArrayList<>();
    private final ControlElse elseStatement;

    public String generateScript() throws IOException {
        var args = new SieveArgument();
        if (id != null) {
            args.appendArgument(filterId(id));
        }
        args.appendArgument(ifCondition(ifStatement));

        if (!elseIfStatements.isEmpty())
            for (var statement : elseIfStatements)
                args.appendArgument(elseIfCondition(statement));

        if (elseStatement != null)
            args.appendArgument(elseCondition(elseStatement));

        var os = new ByteArrayOutputStream();
        args.write(os);
        return os.toString();
    }

    private SieveArgument filterId(UUID id) {
        var args = new SieveArgument();
        return args.writeAtom(String.format("# Filter: %s\n", id));
    }

    private SieveArgument ifCondition(ControlIf ifStatement) {
        var condition = generateConditions(ifStatement.getCondition());
        var actions = ifStatement.getActions().stream().map(this::generateActions).toList();
        return new SieveArgument().writeConditionBlock(
                "if",
                condition,
                actions
        );
    }

    private SieveArgument elseIfCondition(ControlElseIf elseIfStatement) {
        var condition = generateConditions(elseIfStatement.getCondition());
        var actions = elseIfStatement.getActions().stream().map(this::generateActions).toList();
        return new SieveArgument().writeConditionBlock(
                "elsif",
                condition,
                actions
        );
    }

    private SieveArgument elseCondition(ControlElse elseStatement) {
        var actions = elseStatement.getActions().stream().map(this::generateActions).toList();
        return new SieveArgument().writeConditionBlock(
                "else",
                null,
                actions
        );
    }

    private SieveArgument generateActions(SieveAction action) {
        if (action instanceof FileIntoAction fileIntoAction)
            return fileInto(fileIntoAction);
        else if (action instanceof RedirectAction redirectAction)
            return redirect(redirectAction);
        else if (action instanceof KeepAction)
            return keep();
        else if (action instanceof DiscardAction)
            return discard();
        else if (action instanceof VacationAction vacationAction)
            return vacation(vacationAction);
        else if (action instanceof AddFlagAction addFlagAction)
            return addFlag(addFlagAction);
        else if (action instanceof DeleteFlagAction deleteFlagAction)
            return deleteFlag(deleteFlagAction);
        else if (action instanceof SetFlagAction setFlagAction)
            return setFlag(setFlagAction);

        throw new IllegalArgumentException();
    }

    private SieveArgument addFlag(AddFlagAction addFlagAction) {
        applyImport(SieveImports.Actions.IMAP4FLAGS);
        return new SieveArgument().writeAtom("addflag").writeStringList(addFlagAction.getFlags());
    }

    private SieveArgument setFlag(SetFlagAction setFlagAction) {
        applyImport(Actions.IMAP4FLAGS);
        return new SieveArgument().writeAtom("setflag").writeStringList(setFlagAction.getFlags());
    }

    private SieveArgument deleteFlag(DeleteFlagAction deleteFlagAction) {
        applyImport(Actions.IMAP4FLAGS);
        return new SieveArgument().writeAtom("removeflag").writeStringList(deleteFlagAction.getFlags());
    }


    private void applyCopy(SieveArgument args, boolean isCopy) {
        if (isCopy) {
            applyImport(Actions.COPY);
            args.writeAtom(":copy");
        }
    }

    private SieveArgument fileInto(FileIntoAction fileIntoAction) {
        applyImport(Actions.FILE_INTO);
        var args = new SieveArgument().writeAtom("fileinto");
        applyCopy(args, fileIntoAction.isCopy());
        return args.writeString(fileIntoAction.getMailbox());
    }

    private SieveArgument redirect(RedirectAction redirectAction) {
        var args = new SieveArgument().writeAtom("redirect");
        applyCopy(args, redirectAction.isCopy());
        return args.writeString(redirectAction.getAddress());
    }
    private SieveArgument keep() {
        return new SieveArgument().writeAtom("keep");
    }

    private SieveArgument discard() {
        return new SieveArgument().writeAtom("discard");
    }

    private SieveArgument vacation(VacationAction vacationAction) {
        applyImport(Actions.VACATION);
        var args = new SieveArgument();
        args.writeAtom("vacation");
        if (vacationAction.getDays() != null)
            args.writeAtom(":days").writeNumber(vacationAction.getDays());

        if (vacationAction.getSubject() != null)
            args.writeAtom(":subject").writeString(vacationAction.getSubject());

        if (vacationAction.getFrom() != null)
            args.writeAtom(":from").writeString(vacationAction.getFrom());

        if (vacationAction.getAddresses() != null && !vacationAction.getAddresses().isEmpty())
            args.writeAtom(":addresses").writeStringList(vacationAction.getAddresses());

        if (vacationAction.getMime() != null)
            args.writeAtom(":mime").writeString(vacationAction.getMime());

        if (vacationAction.getHandle() != null)
            args.writeAtom(":handle").writeString(vacationAction.getHandle());

        if (vacationAction.getReason() != null)
            args.writeString(vacationAction.getReason());

        return args;
    }

    private SieveArgument generateConditions(SieveCondition condition) {
        if (condition instanceof AndCondition andCondition)
            return and(andCondition);
        else if (condition instanceof OrCondition orCondition)
            return or(orCondition);
        else if (condition instanceof NotCondition notCondition)
            return not(notCondition);
        else if (condition instanceof HeaderCondition headerCondition)
            return header(headerCondition);
        else if (condition instanceof EnvelopeCondition envelopeCondition)
            return envelope(envelopeCondition);
        else if (condition instanceof AddressCondition addressCondition)
            return address(addressCondition);
        else if (condition instanceof SizeCondition sizeCondition)
            return size(sizeCondition);
        else if (condition instanceof ExistsCondition existsCondition)
            return exists(existsCondition);
        else if (condition instanceof TrueCondition)
            return _True();
        else if (condition instanceof FalseCondition)
            return _False();

        throw new IllegalArgumentException();
    }

    private SieveArgument and(AndCondition andCondition) {
        var mappedConditions = andCondition.getConditions()
                .stream().map(this::generateConditions).toList();
        return new SieveArgument()
                .writeTest("allof", mappedConditions);

    }

    private SieveArgument or(OrCondition orCondition) {
        var mappedConditions = orCondition.getConditions()
                .stream().map(this::generateConditions).toList();
        return new SieveArgument()
                .writeTest("anyof", mappedConditions);
    }

    private SieveArgument not(NotCondition notCondition) {
        var condition = generateConditions(notCondition.getCondition());
        return new SieveArgument().writeAtom("not").appendArgument(condition);
    }

    private SieveArgument header(HeaderCondition headerCondition) {
        return new SieveArgument()
                .writeAtom("header")
                .writeAtom(headerCondition.getMatchType().getSyntax())
                .writeStringList(headerCondition.getHeaders())
                .writeStringList(headerCondition.getKeys());
    }

    private SieveArgument envelope(EnvelopeCondition envelopeCondition) {
        applyImport(Conditions.ENVELOPE);
        return new SieveArgument()
                .writeAtom("envelope")
                .writeAtom(envelopeCondition.getAddressPart().getSyntax())
                .writeAtom(envelopeCondition.getMatchType().getSyntax())
                .writeStringList(envelopeCondition.getEnvelopeParts())
                .writeStringList(envelopeCondition.getKeys());
    }

    private SieveArgument address(AddressCondition addressCondition) {
        return new SieveArgument()
                .writeAtom("address")
                .writeAtom(addressCondition.getAddressPart().getSyntax())
                .writeAtom(addressCondition.getMatchType().getSyntax())
                .writeStringList(addressCondition.getHeaders())
                .writeStringList(addressCondition.getKeys());
    }

    private SieveArgument size(SizeCondition sizeCondition) {
        return new SieveArgument()
                .writeAtom("size")
                .writeAtom(sizeCondition.getSizeType().getSyntax())
                .writeNumber(sizeCondition.getSize());
    }

    private SieveArgument exists(ExistsCondition existsCondition) {
        return new SieveArgument()
                .writeAtom("exists")
                .writeStringList(existsCondition.getHeaders());
    }

    private SieveArgument _True() {
        return new SieveArgument().writeAtom("true");
    }

    private SieveArgument _False() {
        return new SieveArgument().writeAtom("false");

    }

    private void applyImport(String capability) {
        imports.addCapability(capability);
    }


}
