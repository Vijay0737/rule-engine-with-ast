package com.yourname.ruleengine.api;

import com.yourname.ruleengine.ast.ExpressionNode;
import com.yourname.ruleengine.data.RuleDatabase;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PolicyManager {
    private RuleDatabase ruleDatabase;

    public PolicyManager() {
        this.ruleDatabase = new RuleDatabase();
    }

    public int addPolicy(String policyString) {
        return ruleDatabase.storePolicy(policyString);
    }

    public ExpressionNode analyzePolicy(String policyString) {
        Stack<ExpressionNode> nodeStack = new Stack<>();
        String[] elements = policyString.split(" ");
        for (String element : elements) {
            if (element.equals("AND") || element.equals("OR")) {
                ExpressionNode rightNode = nodeStack.pop();
                ExpressionNode leftNode = nodeStack.pop();
                nodeStack.push(new ExpressionNode("operator", leftNode, rightNode, element));
            } else {
                nodeStack.push(new ExpressionNode("operand", null, null, element));
            }
        }
        return nodeStack.pop();
    }

    public ExpressionNode mergePolicies(List<String> policies) {
        ExpressionNode mergedRoot = null;
        for (String policy : policies) {
            ExpressionNode policyNode = analyzePolicy(policy);
            if (mergedRoot == null) {
                mergedRoot = policyNode;
            } else {
                mergedRoot = new ExpressionNode("operator", mergedRoot, policyNode, "AND");
            }
        }
        return mergedRoot;
    }

    public boolean checkPolicy(ExpressionNode ast, Map<String, Object> context) {
        if (ast == null) {
            return false;
        }
        if (ast.getType().equals("operator")) {
            if (ast.getValue().equals("AND")) {
                return checkPolicy(ast.getLeft(), context) && checkPolicy(ast.getRight(), context);
            } else if (ast.getValue().equals("OR")) {
                return checkPolicy(ast.getLeft(), context) || checkPolicy(ast.getRight(), context);
            }
        } else if (ast.getType().equals("operand")) {
            String[] conditionParts = ast.getValue().split("=");
            String attribute = conditionParts[0].trim();
            String value = conditionParts[1].trim().replace("'", "");
            return context.get(attribute).toString().equals(value);
        }
        return false;
    }

    public Map<Integer, String> retrieveAllPolicies() {
        return ruleDatabase.getPolicies();
    }
}
