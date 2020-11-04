package com.lc.misc.spring.elTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * TODO
 *
 * @author luochao
 * @since 2020-11-04 17:04
 */
public class BaseTest {
    private static final EvaluationContext evaluationContext = new StandardEvaluationContext();

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static void main(String[] args) {
        User user = new User("a1", "id1", "man1");
        User user2 = new User("a2", "id2", "man2");
        Expression expression = EXPRESSION_PARSER.parseExpression("#user1.name");
        Expression expression2 = EXPRESSION_PARSER.parseExpression("#user2.gender");
        evaluationContext.setVariable("user1",user);
        evaluationContext.setVariable("user2",user2);
        String value = expression.getValue(evaluationContext, String.class);
        String value2 = expression2.getValue(evaluationContext, String.class);
        System.out.println(value);
        System.out.println(value2);
    }

    @AllArgsConstructor
    @Data
    static class User{
        String name;
        String id;
        String gender;
    }
}
