package com.abdulaziz1928.builder;

import com.abdulaziz1928.builder.actions.*;
import com.abdulaziz1928.builder.conditions.*;
import com.abdulaziz1928.builder.control.ControlElse;
import com.abdulaziz1928.builder.control.ControlElseIf;
import com.abdulaziz1928.builder.control.ControlIf;
import com.abdulaziz1928.builder.control.ControlRequire;
import com.abdulaziz1928.builder.types.BodyTransformType;
import com.abdulaziz1928.builder.types.Comparator;
import com.abdulaziz1928.builder.types.MatchType;
import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static com.abdulaziz1928.builder.SieveImports.*;

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
        if (Objects.nonNull(id)) {
            args.appendArgument(filterId(id));
        }
        args.appendArgument(ifCondition(ifStatement));

        if (!elseIfStatements.isEmpty())
            for (var statement : elseIfStatements)
                args.appendArgument(elseIfCondition(statement));

        if (Objects.nonNull(elseStatement))
            args.appendArgument(elseCondition(elseStatement));

        var os = new ByteArrayOutputStream();
        args.write(os);
        os.flush();
        return os.toString();
    }

    private SieveArgument filterId(UUID id) {
        return new SieveArgument().writeAtom(String.format("# Filter: %s\r\n", id));
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
        else if (action instanceof KeepAction keepAction)
            return keep(keepAction);
        else if (action instanceof DiscardAction)
            return discard();
        else if (action instanceof VacationAction vacationAction)
            return vacation(vacationAction);
        else if (action instanceof AddFlagAction addFlagAction)
            return addFlag(addFlagAction);
        else if (action instanceof RemoveFlagAction removeFlagAction)
            return removeFlag(removeFlagAction);
        else if (action instanceof SetFlagAction setFlagAction)
            return setFlag(setFlagAction);
        else if (action instanceof StopAction)
            return stop();
        else if (action instanceof CustomSieveAction customSieveAction)
            return generateCustomAction(customSieveAction);

        throw new IllegalArgumentException("action not supported");
    }

    private SieveArgument generateCustomAction(CustomSieveAction action) {
        var args = action.generateAction();
        applyImports(action.getImports());
        return args;
    }

    private SieveArgument stop() {
        return new SieveArgument().writeAtom("stop");
    }

    private SieveArgument addFlag(AddFlagAction addFlagAction) {
        return flag(addFlagAction, "addflag");
    }

    private SieveArgument setFlag(SetFlagAction setFlagAction) {
        return flag(setFlagAction, "setflag");
    }

    private SieveArgument removeFlag(RemoveFlagAction removeFlagAction) {
        return flag(removeFlagAction, "removeflag");
    }

    private SieveArgument flag(AbstractFlagAction flagAction, String name) {
        applyImport(Common.IMAP4FLAGS);
        var args = new SieveArgument().writeAtom(name);

        if (Objects.nonNull(flagAction.getVariableName())) {
            applyImport(Common.VARIABLES);
            args.writeString(flagAction.getVariableName());
        }
        return args.writeStringList(flagAction.getFlags());
    }


    private void applyCopy(SieveArgument args, boolean isCopy) {
        if (isCopy) {
            applyImport(Actions.COPY);
            args.writeAtom(":copy");
        }
    }

    private SieveArgument redirect(RedirectAction redirectAction) {
        var args = new SieveArgument().writeAtom("redirect");
        applyCopy(args, redirectAction.isCopy());
        return args.writeString(redirectAction.getAddress());
    }

    private SieveArgument fileInto(FileIntoAction fileIntoAction) {
        applyImport(Actions.FILE_INTO);
        var args = new SieveArgument().writeAtom("fileinto");
        applyFlags(args, fileIntoAction.getFlagsVariableName(), fileIntoAction.getFlags());
        applyCopy(args, fileIntoAction.isCopy());
        return args.writeString(fileIntoAction.getMailbox());
    }

    private SieveArgument keep(KeepAction keepAction) {
        var args = new SieveArgument().writeAtom("keep");
        applyFlags(args, keepAction.getFlagsVariableName(), keepAction.getFlags());
        return args;
    }

    private void applyFlags(SieveArgument args, String flagVariable, List<String> flags) {
        if (Objects.nonNull(flagVariable)) {
            applyImport(Common.IMAP4FLAGS);
            applyImport(Common.VARIABLES);
            args.writeAtom(":flags").writeString(String.format("${%s}", flagVariable));
        } else if (Objects.nonNull(flags) && !flags.isEmpty()) {
            applyImport(Common.IMAP4FLAGS);
            args.writeAtom(":flags").writeStringList(flags);
        }
    }

    private SieveArgument discard() {
        return new SieveArgument().writeAtom("discard");
    }

    private SieveArgument vacation(VacationAction vacationAction) {
        applyImport(Actions.VACATION);
        var args = new SieveArgument().writeAtom("vacation");

        if (Objects.nonNull(vacationAction.getDays()))
            args.writeAtom(":days").writeNumber(vacationAction.getDays());

        if (Objects.nonNull(vacationAction.getSubject()))
            args.writeAtom(":subject").writeString(vacationAction.getSubject());

        if (Objects.nonNull(vacationAction.getFrom()))
            args.writeAtom(":from").writeString(vacationAction.getFrom());

        if (Objects.nonNull(vacationAction.getAddresses()) && !vacationAction.getAddresses().isEmpty())
            args.writeAtom(":addresses").writeStringList(vacationAction.getAddresses());

        if (Objects.nonNull(vacationAction.getHandle()))
            args.writeAtom(":handle").writeString(vacationAction.getHandle());

        if (Objects.nonNull(vacationAction.getMime()))
            args.writeAtom(":mime")
                    .writeAtom(String.format("\"%s\"",
                            (vacationAction.getMime().replace("\"", "\\\""))));

        if (Objects.nonNull(vacationAction.getReason()))
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
        else if (condition instanceof HasFlagCondition hasFlagCondition)
            return hasFlag(hasFlagCondition);
        else if (condition instanceof TrueCondition)
            return _True();
        else if (condition instanceof FalseCondition)
            return _False();
        else if (condition instanceof BodyCondition bodyCondition)
            return body(bodyCondition);
        else if (condition instanceof CustomSieveCondition customSieveCondition)
            return generateCustomCondition(customSieveCondition);

        throw new IllegalArgumentException("condition not supported");
    }

    private SieveArgument body(BodyCondition bodyCondition) {
        applyImport(Conditions.BODY);
        var args = new SieveArgument().writeAtom("body");

        applyComparator(args, bodyCondition.getComparator());
        applyMatchType(args, bodyCondition.getMatchType());

        var transform = bodyCondition.getBodyTransform();
        if (Objects.nonNull(transform)) {
            args.writeAtom(transform.getType().getSyntax());
            if (BodyTransformType.CONTENT.equals(transform.getType())) {
                args.writeStringList(transform.getContentList());
            }
        }

        return args.writeStringList(bodyCondition.getKeys());
    }

    private SieveArgument generateCustomCondition(CustomSieveCondition condition) {
        var args = condition.generateCondition();
        applyImports(condition.getImports());
        return args;
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

    private SieveArgument hasFlag(HasFlagCondition hasFlagCondition) {
        applyImport(Common.IMAP4FLAGS);
        var args = new SieveArgument().writeAtom("hasflag");

        applyMatchType(args, hasFlagCondition.getMatchType());
        applyComparator(args, hasFlagCondition.getComparator());

        if (Objects.nonNull(hasFlagCondition.getVariables()) && !hasFlagCondition.getVariables().isEmpty()) {
            applyImport(Common.VARIABLES);
            args.writeStringList(hasFlagCondition.getVariables());
        }
        return args.writeStringList(hasFlagCondition.getFlags());
    }

    private SieveArgument header(HeaderCondition headerCondition) {
        var args = new SieveArgument().writeAtom("header");

        applyComparator(args, headerCondition.getComparator());
        applyMatchType(args, headerCondition.getMatchType());

        return args
                .writeStringList(headerCondition.getHeaders())
                .writeStringList(headerCondition.getKeys());
    }

    private SieveArgument envelope(EnvelopeCondition envelopeCondition) {
        applyImport(Conditions.ENVELOPE);
        var args = new SieveArgument().writeAtom("envelope");

        applyComparator(args, envelopeCondition.getComparator());

        if (Objects.nonNull(envelopeCondition.getAddressPart()))
            args.writeAtom(envelopeCondition.getAddressPart().getSyntax());

        applyMatchType(args, envelopeCondition.getMatchType());

        return args
                .writeStringList(envelopeCondition.getEnvelopeParts())
                .writeStringList(envelopeCondition.getKeys());
    }

    private SieveArgument address(AddressCondition addressCondition) {
        var args = new SieveArgument().writeAtom("address");

        applyComparator(args, addressCondition.getComparator());

        if (Objects.nonNull(addressCondition.getAddressPart()))
            args.writeAtom(addressCondition.getAddressPart().getSyntax());

        applyMatchType(args, addressCondition.getMatchType());


        return args
                .writeStringList(addressCondition.getHeaders())
                .writeStringList(addressCondition.getKeys());

    }

    private static void applyComparator(SieveArgument args, Comparator comparator) {
        if (Objects.nonNull((comparator)))
            args.writeAtom(":comparator").writeString(comparator.getName());
    }

    private static void applyMatchType(SieveArgument args, MatchType matchType) {
        if (Objects.nonNull(matchType))
            args.writeAtom(matchType.getSyntax());
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

    private void applyImports(ControlRequire imports) {
        this.imports.addCapabilities(imports.getCapabilities());
    }

}
