package ci.interpreters.posh;

import java.util.List;

abstract class Expr {
    interface Visitor<R> {
        R visitAssignExpr(Assign expr);

        R visitBinaryExpr(Binary expr);

        R visitCallExpr(Call expr);

        R visitGetExpr(Get expr);

        R visitGroupingExpr(Grouping expr);

        R visitLiteralExpr(Literal expr);

        R visitLogicalExpr(Logical expr);

        R visitSetExpr(Set expr);

        R visitSuperExpr(Super expr);

        R visitThisExpr(This expr);

        R visitUnaryExpr(Unary expr);

        R visitVariableExpr(Variable expr);
    }

    // Nested Expr classes here...
    // > expr-assign
    static class Assign extends Expr {
        Assign(token name, Expr value) {
            this.name = name;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }

        final token name;
        final Expr value;
    }

    // < expr-assign
    // > expr-binary
    static class Binary extends Expr {
        Binary(Expr left, token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        final Expr left;
        final token operator;
        final Expr right;
    }

    // < expr-binary
    // > expr-call
    static class Call extends Expr {
        Call(Expr callee, token paren, List<Expr> arguments) {
            this.callee = callee;
            this.paren = paren;
            this.arguments = arguments;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }

        final Expr callee;
        final token paren;
        final List<Expr> arguments;
    }

    // < expr-call
    // > expr-get
    static class Get extends Expr {
        Get(Expr object, token name) {
            this.object = object;
            this.name = name;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGetExpr(this);
        }

        final Expr object;
        final token name;
    }

    // < expr-get
    // > expr-grouping
    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        final Expr expression;
    }

    // < expr-grouping
    // > expr-literal
    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        final Object value;
    }

    // < expr-literal
    // > expr-logical
    static class Logical extends Expr {
        Logical(Expr left, token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        final Expr left;
        final token operator;
        final Expr right;
    }

    // < expr-logical
    // > expr-set
    static class Set extends Expr {
        Set(Expr object, token name, Expr value) {
            this.object = object;
            this.name = name;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSetExpr(this);
        }

        final Expr object;
        final token name;
        final Expr value;
    }

    // < expr-set
    // > expr-super
    static class Super extends Expr {
        Super(token keyword, token method) {
            this.keyword = keyword;
            this.method = method;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSuperExpr(this);
        }

        final token keyword;
        final token method;
    }

    // < expr-super
    // > expr-this
    static class This extends Expr {
        This(token keyword) {
            this.keyword = keyword;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitThisExpr(this);
        }

        final token keyword;
    }

    // < expr-this
    // > expr-unary
    static class Unary extends Expr {
        Unary(token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        final token operator;
        final Expr right;
    }

    // < expr-unary
    // > expr-variable
    static class Variable extends Expr {
        Variable(token name) {
            this.name = name;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }

        final token name;
    }
    // < expr-variable

    abstract <R> R accept(Visitor<R> visitor);
}
