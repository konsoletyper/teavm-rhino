package org.mozilla.javascript.resources;

import org.mozilla.javascript.ScriptRuntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* OPT there's a noticable delay for the first error!  Maybe it'd
 * make sense to use a ListResourceBundle instead of a properties
 * file to avoid (synchronized) text parsing.
 */
public class DefaultMessageProvider implements ScriptRuntime.MessageProvider {
    private Map<String, String> messages = new HashMap<>();

    public DefaultMessageProvider() {
        messages.put("msg.non.js.object.warning", "RHINO USAGE WARNING: Missed Context.javaToJS() conversion: "
            + "Rhino runtime detected object \"{0}\" of class \"{1}\" where it "
            + "expected String, Number, Boolean or Scriptable instance. Please check your code for "
            + "missing Context.javaToJS() call.");

        messages.put("msg.dup.parms", "Duplicate parameter name \"{0}\".");
        messages.put("msg.too.big.jump", "Program too complex: too big jump offset.");
        messages.put("msg.too.big.index", "Program too complex: internal index exceeds 64K limit.");
        messages.put("msg.while.compiling.fn",
            "Encountered code generation error while compiling function \"{0}\": {1}");
        messages.put("msg.while.compiling.script", "Encountered code generation error while compiling script: {0}");

        messages.put("msg.ctor.not.found", " Constructor for \"{0}\" not found.");
        messages.put("msg.not.ctor", "\"{0}\" is not a constructor.");

        messages.put("msg.incompat.call", "Method \"{0}\" called on incompatible object.");

        messages.put("msg.bad.for.in.lhs", "Invalid left-hand side of for..in loop.");
        messages.put("msg.mult.index", "Only one variable allowed in for..in loop.");
        messages.put("msg.bad.for.in.destruct", "Left hand side of for..in loop must be an array of length 2 to accept"
            + "key/value pair.");
        messages.put("msg.bad.assign.left", "Invalid assignment left-hand side.");
        messages.put("msg.bad.decr", "Invalid decrement operand.");
        messages.put("msg.bad.incr", "Invalid increment operand.");
        messages.put("msg.bad.yield", "yield must be in a function.");
        messages.put("msg.yield.parenthesized", "yield expression must be parenthesized.");

        messages.put("msg.cant.call.indirect", "Function \"{0}\" must be called directly, "
            + "and not by way of a function of another name.");
        messages.put("msg.eval.nonstring", "Calling eval() with anything other than a primitive string value will "
            + "simply return the value. Is this what you intended?");
        messages.put("msg.eval.nonstring.strict", "Calling eval() with anything other than "
            + "a primitive string value is not allowed in strict mode.");
        messages.put("msg.bad.destruct.op", "Invalid destructuring assignment operator");

        messages.put("msg.only.from.new", "\"{0}\" may only be invoked from a \"new\" expression.");
        messages.put("msg.deprec.ctor", "The \"{0}\" constructor is deprecated.");

        messages.put("msg.no.function.ref.found", "no source found to decompile function reference {0}");
        messages.put("msg.arg.isnt.array", "second argument to Function.prototype.apply must be an array");

        messages.put("msg.bad.esc.mask", "invalid string escape mask");

        messages.put("msg.bad.quant", "Invalid quantifier {0}");
        messages.put("msg.overlarge.backref", "Overly large back reference {0}");
        messages.put("msg.overlarge.min", "Overly large minimum {0}");
        messages.put("msg.overlarge.max", "Overly large maximum {0}");
        messages.put("msg.zero.quant", "Zero quantifier {0}");
        messages.put("msg.max.lt.min", "Maximum {0} less than minimum");
        messages.put("msg.unterm.quant", "Unterminated quantifier {0}");
        messages.put("msg.unterm.paren", "Unterminated parenthetical {0}");
        messages.put("msg.unterm.class", "Unterminated character class {0}");
        messages.put("msg.bad.range", "Invalid range in character class.");
        messages.put("msg.trail.backslash", "Trailing \\\\ in regular expression.");
        messages.put("msg.re.unmatched.right.paren", "unmatched ) in regular expression.");
        messages.put("msg.no.regexp", "Regular expressions are not available.");
        messages.put("msg.bad.backref", "back-reference exceeds number of capturing parentheses.");
        messages.put("msg.bad.regexp.compile", "Only one argument may be specified if the first argument "
            + "to RegExp.prototype.compile is a RegExp object.");
        messages.put("msg.arg.not.object", "Expected argument of type object, but instead had type {0}");

        messages.put("msg.invalid.date", "Date is invalid.");
        messages.put("msg.toisostring.must.return.primitive", "toISOString must return a primitive value, "
            + "but instead returned \"{0}\"");

        messages.put("msg.got.syntax.errors", "Compilation produced {0} syntax errors.");
        messages.put("msg.var.redecl", "TypeError: redeclaration of var {0}.");
        messages.put("msg.const.redecl", "TypeError: redeclaration of const {0}.");
        messages.put("msg.let.redecl", "TypeError: redeclaration of variable {0}.");
        messages.put("msg.parm.redecl", "TypeError: redeclaration of formal parameter {0}.");
        messages.put("msg.fn.redecl", "TypeError: redeclaration of function {0}.");
        messages.put("msg.let.decl.not.in.block", "SyntaxError: let declaration not directly within block");
        messages.put("msg.bad.object.init", "SyntaxError: invalid object initializer");

        messages.put("msg.dup.label", "duplicated label");
        messages.put("msg.undef.label", "undefined label");
        messages.put("msg.bad.break", "unlabelled break must be inside loop or switch");
        messages.put("msg.continue.outside", "continue must be inside loop");
        messages.put("msg.continue.nonloop", "continue can only use labels of iteration statements");
        messages.put("msg.bad.throw.eol", "Line terminator is not allowed between the throw keyword and throw "
            + "expression.");
        messages.put("msg.no.paren.parms", "missing ( before function parameters.");
        messages.put("msg.no.parm", "missing formal parameter");
        messages.put("msg.no.paren.after.parms", "missing ) after formal parameters");
        messages.put("msg.no.brace.body", "missing { before function body");
        messages.put("msg.no.brace.after.body", "missing } after function body");
        messages.put("msg.no.paren.cond", "missing ( before condition");
        messages.put("msg.no.paren.after.cond", "missing ) after condition");
        messages.put("msg.no.semi.stmt", "missing ; before statement");
        messages.put("msg.missing.semi", "missing ; after statement");
        messages.put("msg.no.name.after.dot", "missing name after . operator");
        messages.put("msg.no.bracket.index", "missing ] in index expression");
        messages.put("msg.no.paren.switch", "missing ( before switch expression");
        messages.put("msg.no.paren.after.switch", "missing ) after switch expression");
        messages.put("msg.no.brace.switch", "missing { before switch body");
        messages.put("msg.bad.switch", "invalid switch statement");
        messages.put("msg.no.colon.case", "missing : after case expression");
        messages.put("msg.double.switch.default", "double default label in the switch statement");
        messages.put("msg.no.while.do", "missing while after do-loop body");
        messages.put("msg.no.paren.for", "missing ( after for");
        messages.put("msg.no.semi.for", "missing ; after for-loop initializer");
        messages.put("msg.no.semi.for.cond", "missing ; after for-loop condition");
        messages.put("msg.in.after.for.name", "missing in after for");
        messages.put("msg.no.paren.for.ctrl", "missing ) after for-loop control");
        messages.put("msg.no.paren.with", "missing ( before with-statement object");
        messages.put("msg.no.paren.after.with", "missing ) after with-statement object");
        messages.put("msg.no.with.strict", "with statements not allowed in strict mode");
        messages.put("msg.no.paren.after.let", "missing ( after let");
        messages.put("msg.no.paren.let", "missing ) after variable list");
        messages.put("msg.no.curly.let", "missing } after let statement");
        messages.put("msg.bad.return", "invalid return");
        messages.put("msg.no.brace.block", "missing } in compound statement");
        messages.put("msg.bad.label", "invalid label");
        messages.put("msg.bad.var", "missing variable name");
        messages.put("msg.bad.var.init", "invalid variable initialization");
        messages.put("msg.no.colon.cond", "missing : in conditional expression");
        messages.put("msg.no.paren.arg", "missing ) after argument list");
        messages.put("msg.no.bracket.arg", "missing ] after element list");
        messages.put("msg.bad.prop", "invalid property id");
        messages.put("msg.no.colon.prop", "missing : after property id");
        messages.put("msg.no.brace.prop", "missing } after property list");
        messages.put("msg.no.paren", "missing ) in parenthetical");
        messages.put("msg.reserved.id", "identifier is a reserved word: {0}");
        messages.put("msg.no.paren.catch", "missing ( before catch-block condition");
        messages.put("msg.bad.catchcond", "invalid catch block condition");
        messages.put("msg.catch.unreachable", "any catch clauses following an unqualified catch are unreachable");
        messages.put("msg.no.brace.try", "missing { before try block");
        messages.put("msg.no.brace.catchblock", "missing { before catch-block body");
        messages.put("msg.try.no.catchfinally", "'try' without 'catch' or 'finally'");
        messages.put("msg.no.return.value", "function {0} does not always return a value");
        messages.put("msg.anon.no.return.value", "anonymous function does not always return a value");
        messages.put("msg.return.inconsistent", "return statement is inconsistent with previous usage");
        messages.put("msg.generator.returns", "TypeError: generator function {0} returns a value");
        messages.put("msg.anon.generator.returns", "TypeError: anonymous generator function returns a value");
        messages.put("msg.syntax", "syntax error");
        messages.put("msg.unexpected.eof", "Unexpected end of file");
        messages.put("msg.too.deep.parser.recursion", "Too deep recursion while parsing");
        messages.put("msg.too.many.constructor.args", "Too many constructor arguments");
        messages.put("msg.too.many.function.args", "Too many function arguments");
        messages.put("msg.no.side.effects", "Code has no side effects");
        messages.put("msg.extra.trailing.semi", "Extraneous trailing semicolon");
        messages.put("msg.extra.trailing.comma", "Trailing comma is not legal in an ECMA-262 object initializer");
        messages.put("msg.trailing.array.comma", "Trailing comma in array literal has different "
            + "cross-browser behavior");
        messages.put("msg.equal.as.assign", "Test for equality (==) mistyped as assignment (=)?");
        messages.put("msg.var.hides.arg", "Variable {0} hides argument");
        messages.put("msg.destruct.assign.no.init", "Missing = in destructuring declaration");
        messages.put("msg.destruct.default.vals", "Default values in destructuring declarations are not supported");
        messages.put("msg.no.old.octal.strict", "Old octal numbers prohibited in strict mode.");
        messages.put("msg.dup.obj.lit.prop.strict", "Property \"{0}\" already defined in this object literal.");
        messages.put("msg.dup.param.strict", "Parameter \"{0}\" already declared in this function.");
        messages.put("msg.bad.id.strict", "\"{0}\" is not a valid identifier for this use in strict mode.");

        messages.put("msg.op.not.allowed", "This operation is not allowed.");
        messages.put("msg.no.properties", "{0} has no properties.");
        messages.put("msg.invalid.iterator", "Invalid iterator value");
        messages.put("msg.iterator.primitive", "__iterator__ returned a primitive value");
        messages.put("msg.not.iterable", "{0} is not iterable");
        messages.put("msg.invalid.for.each", "invalid for each loop");
        messages.put("msg.assn.create.strict", "Assignment to undeclared variable \"{0}\"");
        messages.put("msg.ref.undefined.prop", "Reference to undefined property \"{0}\"");
        messages.put("msg.prop.not.found", "Property \"{0}\" not found.");
        messages.put("msg.set.prop.no.setter", "Cannot set property \"{0}\" that has only a getter to value \"{1}\".");
        messages.put("msg.invalid.type", "Invalid JavaScript value of type {0}");
        messages.put("msg.primitive.expected", "Primitive type expected (had {0} instead)");
        messages.put("msg.null.to.object", "Cannot convert null to an object.");
        messages.put("msg.undef.to.object", "Cannot convert undefined to an object.");
        messages.put("msg.cyclic.value", "Cyclic {0} value not allowed.");
        messages.put("msg.is.not.defined", "\"{0}\" is not defined.");
        messages.put("msg.undef.prop.read", "Cannot read property \"{1}\" from {0}");
        messages.put("msg.undef.prop.write", "Cannot set property \"{1}\" of {0} to \"{2}\"");
        messages.put("msg.undef.prop.delete", "Cannot delete property \"{1}\" of {0}");
        messages.put("msg.undef.method.call", "Cannot call method \"{1}\" of {0}");
        messages.put("msg.undef.with", "Cannot apply \"with\" to {0}");
        messages.put("msg.isnt.function", "\"{0}\" is not a function, it is {1}.");
        messages.put("msg.isnt.function.in", "Cannot call property \"{0}\" in object {1}. "
            + "It is not a function, it is {2}");
        messages.put("msg.function.not.found", "Cannot find function \"{0}\".");
        messages.put("msg.function.not.found.in", "Cannot find function \"{0}\" in object {1}.");
        messages.put("msg.no.ref.from.function", "Function \"{0}\" can not be used as the left-hand side of assignment "
            + "or as an operand of ++ or -- operator.");
        messages.put("msg.bad.default.value", "Object's getDefaultValue() method returned an object.");
        messages.put("msg.instanceof.not.object", "Can't use 'instanceof' on a non-object.");
        messages.put("msg.instanceof.bad.prototype", "'prototype' property of {0} is not an object.");
        messages.put("msg.in.not.object", "Can't use 'in' on a non-object.");
        messages.put("msg.bad.radix", "illegal radix {0}.");

        messages.put("msg.default.value", "Cannot find default value for object.");
        messages.put("msg.extend.scriptable", "{0} must extend ScriptableObject in order to define property "
            + "\"{1}\".");
        messages.put("msg.modify.readonly", "Cannot modify readonly property \"{0}\".");
        messages.put("msg.both.data.and.accessor.desc", "Cannot be both a data and an accessor descriptor.");
        messages.put("msg.change.configurable.false.to.true", "Cannot change the configurable attribute of "
            + "\"{0}\" from false to true.");
        messages.put("msg.change.enumerable.with.configurable.false", "Cannot change the enumerable attribute "
            + "of \"{0}\" because configurable is false.");
        messages.put("msg.change.writable.false.to.true.with.configurable.false", "Cannot change the writable "
            + "attribute of \"{0}\" from false to true because configurable is false.");
        messages.put("msg.change.value.with.writable.false", "Cannot change the value of "
            + "attribute \"{0}\" because writable is false.");
        messages.put("msg.change.getter.with.configurable.false", "Cannot change the get attribute of "
            + "\"{0}\" because configurable is false.");
        messages.put("msg.change.setter.with.configurable.false", "Cannot change the set attribute of "
            + "\"{0}\" because configurable is false.");
        messages.put("msg.change.property.data.to.accessor.with.configurable.false", "Cannot change \"{0}\" from a "
            + "data property to an accessor property because configurable is false.");
        messages.put("msg.change.property.accessor.to.data.with.configurable.false", "Cannot change \"{0}\" from an "
            + "accessor property to a data property because configurable is false.");
        messages.put("msg.not.extensible", "Cannot add properties to this object because extensible is false.");
        messages.put("msg.delete.property.with.configurable.false", "Cannot delete \"{0}\" property "
            + "because configurable is false.");

        messages.put("msg.missing.exponent", "missing exponent");
        messages.put("msg.caught.nfe", "number format error");
        messages.put("msg.unterminated.string.lit", "unterminated string literal");
        messages.put("msg.unterminated.comment", "unterminated comment");
        messages.put("msg.unterminated.re.lit", "unterminated regular expression literal");
        messages.put("msg.invalid.re.flag", "invalid flag after regular expression");
        messages.put("msg.no.re.input.for", "no input for {0}");
        messages.put("msg.illegal.character", "illegal character: {0}");
        messages.put("msg.invalid.escape", "invalid Unicode escape sequence");

        messages.put("msg.bad.octal.literal", "illegal octal literal digit {0}; interpreting it as a decimal digit");
        messages.put("msg.reserved.keyword", "illegal usage of future reserved keyword {0}; "
            + "interpreting it as ordinary identifier");
        messages.put("msg.script.is.not.constructor", "Script objects are not constructors.");

        messages.put("msg.arraylength.bad", "Inappropriate array length.");
        messages.put("msg.arraylength.too.big", "Array length {0} exceeds supported capacity limit.");
        messages.put("msg.empty.array.reduce", "Reduce of empty array with no initial value");

        messages.put("msg.bad.uri", "Malformed URI sequence.");
        messages.put("msg.bad.precision", "Precision {0} out of range.");

        messages.put("msg.send.newborn", "Attempt to send value to newborn generator");
        messages.put("msg.already.exec.gen", "Already executing generator");
        messages.put("msg.StopIteration.invalid", "StopIteration may not be changed to an arbitrary object.");
        messages.put("msg.yield.closing", "Yield from closing generator");
        messages.put("msg.called.null.or.undefined", "{0}.prototype.{1} method called on null or undefined");
        messages.put("msg.first.arg.not.regexp", "First argument to {0}.prototype.{1} must not be a "
            + "regular expression");
        messages.put("msg.arrowfunction.generator", "arrow function can not become generator");
        messages.put("msg.arguments.not.access.strict", "Cannot access \"{0}\" property of the arguments "
            + "object in strict mode.");

        messages.put("msg.object.not.symbolscriptable", "Object {0} does not support Symbol keys");
        messages.put("msg.no.assign.symbol.strict", "Symbol objects may not be assigned properties in strict mode");
        messages.put("msg.not.a.string", "The object is not a string");
        messages.put("msg.not.a.number", "The object is not a number");
        messages.put("msg.no.symbol.new", "Symbol objects may not be constructed using \"new\"");
        messages.put("msg.compare.symbol", "Symbol objects may not be compared");
        messages.put("msg.no.new", "{0} objects may not be constructed using \"new\"");
    }

    @Override
    public String getMessage(String messageId, Object[] arguments) {
        String template = messages.get(messageId);
        if (template == null) {
            return messageId + (arguments != null && arguments.length != 0 ? ": " + Arrays.toString(arguments) : "");
        }

        StringBuilder sb = new StringBuilder();
        int index = 0;
        outer: while (true) {
            int next = index;

            int parameter;
            int close;
            while (true) {
                next = template.indexOf('{', next);
                if (next < 0) {
                    break outer;
                }

                close = template.indexOf('}', next + 1);
                if (close < 0) {
                    break outer;
                }

                if (!isNumber(template, next + 1, close)) {
                    next = close + 1;
                    continue;
                }

                parameter = Integer.parseInt(template.substring(next + 1, close));
                break;
            };

            sb.append(template, index, next);
            if (parameter >= arguments.length) {
                sb.append("{").append(parameter).append("}");
            } else {
                sb.append(arguments[parameter]);
            }
            index = close + 1;
        }

        sb.append(template, index, template.length());

        return sb.toString();
    }

    private static boolean isNumber(String text, int start, int end) {
        if (start == end) {
            return false;
        }
        while (start < end) {
            if (!isDigit(text.charAt(start))) {
                return false;
            }
            start++;
        }
        return true;
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
