package com.example.kernlang.compiler.parser;

import java.util.Optional;

public record ParseResult(Optional<ASTNode> syntaxNode, String leftOverString, String optionalErrMsg) {
}
