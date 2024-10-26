package com.yourname.ruleengine.ui;

import com.yourname.ruleengine.api.PolicyManager;
import com.yourname.ruleengine.ast.ExpressionNode;

import java.util.*;

public class PolicyEngineInterface {
    private PolicyManager policyManager;

    public PolicyEngineInterface() {
        this.policyManager = new PolicyManager();
    }

    public void launch() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Policy");
            System.out.println("2. Combine Policies");
            System.out.println("3. Evaluate Policy");
            System.out.println("4. View All Policies");
            System.out.println("5. Exit");

            int selection = inputScanner.nextInt();
            inputScanner.nextLine(); // consume newline

            switch (selection) {
                case 1:
                    System.out.println("Enter policy:");
                    String policy = inputScanner.nextLine();
                    int policyId = policyManager.addPolicy(policy);
                    System.out.println("Policy created with ID: " + policyId);
                    break;
                case 2:
                    System.out.println("Enter policy IDs to combine (comma-separated):");
                    String ids = inputScanner.nextLine();
                    List<String> policiesToCombine = new ArrayList<>();
                    for (String id : ids.split(",")) {
                        policiesToCombine.add(policyManager.retrievePolicies().get(Integer.parseInt(id.trim())));
                    }
                    ExpressionNode combinedPolicy = policyManager.mergePolicies(policiesToCombine);
                    System.out.println("Combined Policy AST: " + combinedPolicy);
                    break;
                case 3:
                    System.out.println("Enter policy ID to evaluate:");
                    int evalPolicyId = inputScanner.nextInt();
                    inputScanner.nextLine(); // consume newline
                    System.out.println("Enter data (format: key=value, comma-separated):");
                    String inputData = inputScanner.nextLine();
                    Map<String, Object> context = new HashMap<>();
                    for (String pair : inputData.split(",")) {
                        String[] keyValue = pair.split("=");
                        context.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                    ExpressionNode policyToEvaluate = policyManager.analyzePolicy(policyManager.retrievePolicies().get(evalPolicyId));
                    boolean evaluationResult = policyManager.checkPolicy(policyToEvaluate, context);
                    System.out.println("Evaluation Result: " + evaluationResult);
                    break;
                case 4:
                    System.out.println("All Policies:");
                    for (Map.Entry<Integer, String> entry : policyManager.retrievePolicies().entrySet()) {
                        System.out.println("ID: " + entry.getKey() + ", Policy: " + entry.getValue());
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    inputScanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        PolicyEngineInterface interfaceInstance = new PolicyEngineInterface();
        interfaceInstance.launch();
    }
}
