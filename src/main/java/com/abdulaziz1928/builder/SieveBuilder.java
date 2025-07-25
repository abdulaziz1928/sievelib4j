package com.abdulaziz1928.builder;

import com.abdulaziz1928.builder.actions.*;
import com.abdulaziz1928.builder.conditions.*;
import com.abdulaziz1928.builder.control.ControlElse;
import com.abdulaziz1928.builder.control.ControlElseIf;
import com.abdulaziz1928.builder.control.ControlIf;
import com.abdulaziz1928.builder.control.ControlRequire;
import com.abdulaziz1928.builder.exceptions.SieveUnsupportedException;
import com.abdulaziz1928.builder.types.*;
import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private final SieveEnclosingBlock enclosingBlock;

    public String generateScript() throws IOException {
        if (Objects.nonNull(enclosingBlock))
            applyImports(enclosingBlock.getImports());

        var args = new SieveArgument(enclosingBlock);

        if (Objects.nonNull(id))
            args.appendArgument(filterId(id));

        args.appendArgument(ifCondition(ifStatement));

        if (Objects.nonNull(elseIfStatements))
            elseIfStatements.forEach(elsif -> args.appendArgument(elseIfCondition(elsif)));

        if (Objects.nonNull(elseStatement))
            args.appendArgument(elseCondition(elseStatement));

        var os = new ByteArrayOutputStream();
        args.write(os);
        os.flush();
        return os.toString();
    }

    private SieveArgument filterId(UUID id) {
        return new SieveArgument().writeComment(String.format("Filter: %s", id));
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
        else if (action instanceof BreakAction)
            return _Break();

        throw new SieveUnsupportedException("action not supported");
    }

    private SieveArgument _Break() {
        return new SieveArgument().writeAtom("break");
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
        else if (condition instanceof DateCondition dateCondition)
            return date(dateCondition);
        else if (condition instanceof CurrentDateCondition currentDateCondition)
            return currentDate(currentDateCondition);
        else if (condition instanceof CustomSieveCondition customSieveCondition)
            return generateCustomCondition(customSieveCondition);

        throw new SieveUnsupportedException("condition not supported");
    }

    private SieveArgument currentDate(CurrentDateCondition dateCondition) {
        applyImport(Conditions.DATE);
        var args = new SieveArgument().writeAtom("currentdate");
        var zone = dateCondition.getDateZone();

        if (Objects.nonNull(zone))
            args.writeAtom(zone.getType().getSyntax()).writeString(zone.getZone());

        applyComparator(args, dateCondition.getComparator());
        applyMatchType(args, dateCondition.getMatch());

        return args
                .writeString(dateCondition.getDatePart().getName())
                .writeStringList(dateCondition.getKeys());
    }

    private SieveArgument date(DateCondition dateCondition) {
        applyImport(Conditions.DATE);
        var args = new SieveArgument().writeAtom("date");
        var zone = dateCondition.getDateZone();

        applyIndex(args, dateCondition.getIndex());

        if (Objects.nonNull(zone)) {
            args.writeAtom(zone.getType().getSyntax());
            if (DateZoneType.ZONE.equals(zone.getType()))
                args.writeString(zone.getZone());
        }

        applyComparator(args, dateCondition.getComparator());
        applyMatchType(args, dateCondition.getMatch());

        return args
                .writeString(dateCondition.getHeader())
                .writeString(dateCondition.getDatePart().getName())
                .writeStringList(dateCondition.getKeys());
    }

    private SieveArgument body(BodyCondition bodyCondition) {
        applyImport(Conditions.BODY);
        var args = new SieveArgument().writeAtom("body");

        applyComparator(args, bodyCondition.getComparator());
        applyMatchType(args, bodyCondition.getMatch());

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

        applyMatchType(args, hasFlagCondition.getMatch());
        applyComparator(args, hasFlagCondition.getComparator());

        if (Objects.nonNull(hasFlagCondition.getVariables()) && !hasFlagCondition.getVariables().isEmpty()) {
            applyImport(Common.VARIABLES);
            args.writeStringList(hasFlagCondition.getVariables());
        }
        return args.writeStringList(hasFlagCondition.getFlags());
    }

    private SieveArgument header(HeaderCondition headerCondition) {
        var args = new SieveArgument().writeAtom("header");
        applyMime(args, headerCondition.getMime(), headerCondition.getAnyChild(), headerCondition.getMimeOpts());
        applyIndex(args, headerCondition.getIndex());
        applyComparator(args, headerCondition.getComparator());
        applyMatchType(args, headerCondition.getMatch());

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

        applyMatchType(args, envelopeCondition.getMatch());

        return args
                .writeStringList(envelopeCondition.getEnvelopeParts())
                .writeStringList(envelopeCondition.getKeys());
    }

    private SieveArgument address(AddressCondition addressCondition) {
        var args = new SieveArgument().writeAtom("address");

        applyMime(args, addressCondition.getMime(), addressCondition.getAnyChild(), null);
        applyIndex(args, addressCondition.getIndex());
        applyComparator(args, addressCondition.getComparator());

        if (Objects.nonNull(addressCondition.getAddressPart()))
            args.writeAtom(addressCondition.getAddressPart().getSyntax());

        applyMatchType(args, addressCondition.getMatch());


        return args
                .writeStringList(addressCondition.getHeaders())
                .writeStringList(addressCondition.getKeys());

    }

    private void applyComparator(SieveArgument args, Comparator comparator) {
        if (Objects.nonNull((comparator))) {
            if (Comparator.ASCII_NUMERIC.equals(comparator))
                applyImport(Comparator.ASCII_NUMERIC.getName());
            args.writeAtom(":comparator").writeString(comparator.getName());
        }
    }

    private void applyMatchType(SieveArgument args, Match match) {
        if (Objects.nonNull(match)) {
            String requiredImport;
            if (Objects.nonNull((requiredImport = match.getMatchType().getRequiredExtension())))
                applyImport(requiredImport);
            args.writeAtom(match.getSyntax());
        }
    }

    private void applyMime(SieveArgument args, Boolean mime, Boolean anyChild, MimeOpts mimeOpts) {
        if (Boolean.TRUE.equals(mime)) {
            applyImport(Conditions.MIME);
            args.writeAtom(":mime");
            if (Boolean.TRUE.equals(anyChild))
                args.writeAtom(":anychild");
            if (Objects.nonNull(mimeOpts)) {
                args.writeAtom(mimeOpts.getType().getSyntax());
                if (Objects.nonNull(mimeOpts.getParamList()))
                    args.writeStringList(mimeOpts.getParamList());
            }
        }
    }

    private void applyIndex(SieveArgument args, Index index) {
        if (Objects.nonNull(index)) {
            applyImport(Conditions.INDEX);
            args.writeAtom(":index").writeNumber(index.getIdx());
            if (index.isLast())
                args.writeAtom(":last");
        }
    }

    private SieveArgument size(SizeCondition sizeCondition) {
        return new SieveArgument()
                .writeAtom("size")
                .writeAtom(sizeCondition.getSizeType().getSyntax())
                .writeNumber(sizeCondition.getSize());
    }

    private SieveArgument exists(ExistsCondition existsCondition) {
        var args = new SieveArgument().writeAtom("exists");
        applyMime(args, existsCondition.getMime(), existsCondition.getAnyChild(), null);
        return args.writeStringList(existsCondition.getHeaders());
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
