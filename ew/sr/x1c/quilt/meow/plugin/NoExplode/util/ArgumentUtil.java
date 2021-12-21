package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

public class ArgumentUtil {

    public static short getOptionalShortArg(String[] arg, int position, short def) {
        if (arg.length > position) {
            try {
                return Short.parseShort(arg[position]);
            } catch (NumberFormatException ex) {
                return def;
            }
        }
        return def;
    }

    public static int getOptionalIntegerArg(String[] arg, int position, int def) {
        if (arg.length > position) {
            try {
                return Integer.parseInt(arg[position]);
            } catch (NumberFormatException ex) {
                return def;
            }
        }
        return def;
    }

    public static long getOptionalLongArg(String[] arg, int position, long def) {
        if (arg.length > position) {
            try {
                return Long.parseLong(arg[position]);
            } catch (NumberFormatException ex) {
                return def;
            }
        }
        return def;
    }

    public static double getOptionalDoubleArg(String[] arg, int position, double def) {
        if (arg.length > position) {
            try {
                return Double.parseDouble(arg[position]);
            } catch (NumberFormatException ex) {
                return def;
            }
        }
        return def;
    }

    public static String getNameArg(String[] arg, int startPos, String name) {
        for (int i = startPos; i < arg.length; ++i) {
            if (arg[i].equalsIgnoreCase(name) && i + 1 < arg.length) {
                return arg[i + 1];
            }
        }
        return null;
    }

    public static Long getNameLongArg(String[] arg, int startPos, String name) {
        String read = getNameArg(arg, startPos, name);
        if (read != null) {
            try {
                return Long.parseLong(read);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    public static Integer getNameIntegerArg(String[] arg, int startPos, String name) {
        String read = getNameArg(arg, startPos, name);
        if (read != null) {
            try {
                return Integer.parseInt(read);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    public static int getNameIntegerArg(String[] arg, int startPos, String name, int def) {
        Integer ret = getNameIntegerArg(arg, startPos, name);
        if (ret == null) {
            return def;
        }
        return ret;
    }

    public static Double getNameDoubleArg(String[] arg, int startPos, String name) {
        String read = getNameArg(arg, startPos, name);
        if (read != null) {
            try {
                return Double.parseDouble(read);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    public static String joinStringFrom(String[] array, int start) {
        return joinStringFrom(array, start, " ");
    }

    public static String joinStringFrom(String[] array, int start, String separate) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < array.length; ++i) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(separate);
            }
        }
        return builder.toString();
    }
}
