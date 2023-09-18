package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents the expression of accessing field from records.
 * Sometimes, a field of a record is a record, of which a field again may be selected:
 * myRecord:field1:field2
 * instead of parsing this recursively (which is hard since it is right-side recursive),
 * we parse all these field-accesses in one go, iteratively.
 */
public class RecordAccess implements ASTNode {

    public ASTNode rootRecord;
    ArrayList<String> fieldnames = new ArrayList<>();


    @Override
    public String toString(String indent) {
        return "\n" + indent + "recordaccess:\n" +
                            "\n\t" + indent + "root record:\n" + rootRecord.toString(indent + "\t\t") +
                            "\n\t" + indent + "fields:\n" +
                        fieldnames.stream().map(s -> indent + "\t\t" + s).collect(Collectors.joining("\n"));
    }

    @Override
    public ParseResult parse(String input) {
        String originalInput = input;
        input = input.stripLeading();

        String[] recordstrings = input.split(":");

        int idx = 0;

        // first recordString is an expression representing a record (identifier or recordliteral)
        if (recordstrings.length > 1) {
            if (recordstrings[0].startsWith("{")) {
                ParseResult res = new RecordLiteral().parse(recordstrings[0]);
                if (res.syntaxNode().isPresent()) {
                    rootRecord = res.syntaxNode().get();
                } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");
            } else {
                String fieldName = recordstrings[0].strip();

                Pattern identifierPattern = Pattern.compile("^[_a-zA-Z][_a-zA-Z0-9]*");
                if (identifierPattern.matcher(fieldName).matches()) {
                    ParseResult res = new IdentifierExpr().parse(recordstrings[0]);
                    if (res.syntaxNode().isPresent()) {
                        rootRecord = res.syntaxNode().get();
                    } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");
                } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");

            }
            idx += recordstrings[0].length() + 1; // add one for the ':' character
        } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");

        for (int i = 1; i < recordstrings.length; i++) {
            String fieldName = recordstrings[i].strip();

            Pattern identifierPattern = Pattern.compile("^[_a-zA-Z][_a-zA-Z0-9]*");
            if (identifierPattern.matcher(fieldName).matches()) {
                fieldnames.add(fieldName);
                idx += recordstrings[i].length() + 1; // add one for the ':' character
            } else break;
        }
        idx -= 1;


        // now there might be one more fieldname, which previously got concatenated with the rest of the input,
        // so we try and see one more time if this is the case
        input = input.substring(idx);
        if (input.startsWith(":")) {
            input = input.substring(1);

            String fieldName = "";
            if (isAlpha(input.charAt(0))) {
                fieldName += input.charAt(0);

                int i = 1;
                while (isAlpha(input.charAt(i)) || isDigit(input.charAt(i))) {
                    fieldName += input.charAt(i);
                    i++;
                }
                fieldnames.add(fieldName);
                input = input.substring(i).stripLeading();
            }
        }

        return new ParseResult(Optional.of(this), input, "");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        RecordLiteral currentRecord = (RecordLiteral) rootRecord.interpret(contextNode, additionalContext);
        // all fieldnames up until the last one indicate a recordliteral
        for (int i = 0; i < fieldnames.size() - 1;i++) {
            currentRecord = (RecordLiteral) currentRecord.getField(fieldnames.get(i));
            if (currentRecord == null) return null;
        }
        return currentRecord.getField(fieldnames.get(fieldnames.size()-1));
    }

    public void assignValue(ASTNode value, GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        /*if (rootRecord instanceof IdentifierExpr) {
            String ident = ((IdentifierExpr) rootRecord).getIdentifier();
            for (GraphEdge edge : contextNode.getImports()) {
                if (edge.getEndNode().name.equals(ident)) {
                    // note: the expressions in the function literal, are to be evaluated in that function's (callee's) context
                    rootRecord = edge.getEndNode().getAST();
                }
            }
        }*/


        RecordLiteral currentRecord = (RecordLiteral) rootRecord.interpret(contextNode, additionalContext);
        /*if (rootRecord instanceof IdentifierExpr) currentRecord = (RecordLiteral) rootRecord.interpret(contextNode, additionalContext);
        else currentRecord = (RecordLiteral) rootRecord;*/
        // all fieldnames up until the last one indicate a recordliteral
        for (int i = 0; i < fieldnames.size() - 1;i++) {
            currentRecord = (RecordLiteral) currentRecord.getField(fieldnames.get(i));
            if (currentRecord == null) return;
        }
        currentRecord.setField(fieldnames.get(fieldnames.size()-1), value);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public ASTNode deepcopy() {
        // why on earth would anyone make copies of unevaluated expressions
        return this;
    }
}
