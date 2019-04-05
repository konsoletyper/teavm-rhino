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
