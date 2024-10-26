package com.yourname.ruleengine.ast;

public class ExpressionNode {
    private String nodeType;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;
    private String nodeValue;

    public ExpressionNode(String nodeType, ExpressionNode leftChild, ExpressionNode rightChild, String nodeValue) {
        this.nodeType = nodeType;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.nodeValue = nodeValue;
    }

    public String getNodeType() {
        return nodeType;
    }

    public ExpressionNode getLeftChild() {
        return leftChild;
    }

    public ExpressionNode getRightChild() {
        return rightChild;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    @Override
    public String toString() {
        return "ExpressionNode{" +
                "nodeType='" + nodeType + '\'' +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                ", nodeValue='" + nodeValue + '\'' +
                '}';
    }
}

