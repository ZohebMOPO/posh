package ci.interpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary: Expr Left, token operator, Expr right",
                "Grouping: Expr expression",
                "Literal: Object value",
                "Unary: token operator, Expr right"));
        defineAst(outputDir, "Stmt", Arrays.asList(
                "Expression: Expr expression",
                "Print: Expr expression"));
    }

    public static void defineAst(String outputDir, String basename, List<String> types) throws IOException {
        String path = outputDir + "/" + basename + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package ci.interpreters.posh");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.print("abstract class " + basename + " {");
        defineVisitor(writer, basename, types);
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, basename, className, fields);
        }
        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String basename, List<String> types) {
        writer.println("  interface Visitor<R> {");

        for (String type : types) {
            String typename = type.split(":")[0].trim();
            writer.println("    R visit" + typename + basename + "(" + typename + " " + basename.toLowerCase() + ");");
        }

        writer.println("  }");

        // The base accept() method.
        writer.println();
        writer.println("  abstract <R> R accept(Visitor<R> visitor);");

    }

    private static void defineType(PrintWriter writer, String basename, String className, String fieldList) {
        writer.println("  static class " + className + " extends " + basename + " {");

        // Constructor.
        writer.print("    " + className + "(" + fieldList + ") {");

        // Store parameters in table.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }

        writer.println("    }");

        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit" +
                className + basename + "(this);");
        writer.println("    }");

        writer.println();
        for (String field : fields) {
            writer.println("    final " + field + ";");
        }

        writer.println("  }");
    }
}