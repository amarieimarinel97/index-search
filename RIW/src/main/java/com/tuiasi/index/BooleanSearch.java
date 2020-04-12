package com.tuiasi.index;

import com.tuiasi.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class BooleanSearch {

    @Autowired
    private IndexUtils indexUtils;

    @Autowired
    private FileUtils fileUtils;

    private Map<String, Map<String, Integer>> inverseIndex = new TreeMap<>();

    public Map<String, Map<String, Integer>> getInverseIndex() {
        return inverseIndex;
    }

    public void setInverseIndex(Map<String, Map<String, Integer>> inverseIndex) {
        this.inverseIndex = inverseIndex;
    }

    public Set<String> andOperation(String A, Set<String> BDocuments) {

        Set<String> ADocuments = new TreeSet<>(inverseIndex.get(indexUtils.getBaseForm(A)).keySet());
        boolean isABiggerThanB = ADocuments.size() > BDocuments.size();
        if (isABiggerThanB) {
            ADocuments.retainAll(BDocuments);
            return ADocuments;
        } else {
            BDocuments.retainAll(ADocuments);
            return BDocuments;
        }
    }


    public Set<String> orOperation(String A, Set<String> BDocuments) {
        Set<String> ADocuments = new TreeSet<>(inverseIndex.get(indexUtils.getBaseForm(A)).keySet());
        boolean isABiggerThanB = ADocuments.size() > BDocuments.size();
        if (isABiggerThanB) {
            ADocuments.addAll(BDocuments);
            return ADocuments;
        } else {
            BDocuments.addAll(ADocuments);
            return BDocuments;
        }
    }


    public Set<String> notOperation(Set<String> ADocuments, String B) {
        Set<String> BDocuments = new TreeSet<>(inverseIndex.get(indexUtils.getBaseForm(B)).keySet());
        ADocuments.removeAll(BDocuments);
        return ADocuments;
    }

    public Set<String> notOperation(String A, Set<String> BDocuments) {
        Set<String> ADocuments = new TreeSet<>(inverseIndex.get(indexUtils.getBaseForm(A)).keySet());
        ADocuments.removeAll(BDocuments);
        return ADocuments;
    }

    public Set<String> evaluateQuery(String query) {
        //& = SI, | = SAU, ! = NOT
        Set<String> result = null;
        List<String> operands = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        char[] chars = query.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == ' ' || c == '\n' || c == '\t')
                continue;
            if (c == '&' || c == '|' || c == '!') {
                operands.add(sb.toString());
                sb = new StringBuilder();
                operators.add(String.valueOf(c));
            } else {
                sb.append(c);
            }
        }
        operands.add(sb.toString());
        Set<String> ASet = null;
        String currentOperator = operators.get(0);
        String operandA = operands.get(0);
        String operandB = operands.get(1);
        ASet = applyOperation(currentOperator, operandA, operandB);

        for (int i = 1; i < operators.size(); ++i) {
            currentOperator = operators.get(i);
            operandB = operands.get(i + 1);
            ASet = applyOperation(currentOperator, ASet, operandB);

        }
        return ASet;
    }

    private Set<String> applyOperation(String currentOperator, String operandA, String operandB) {
        return applyOperation(currentOperator, new TreeSet<>(inverseIndex.get(indexUtils.getBaseForm(operandA)).keySet()), operandB);
    }

    private Set<String> applyOperation(String currentOperator, Set<String> ADocuments, String operandB) {
        switch (currentOperator) {
            case "&":
                return andOperation(operandB, ADocuments);
            case "|":
                return orOperation(operandB, ADocuments);
            case "!":
                return notOperation(ADocuments, operandB);
            default:
                System.out.println("Operator necunoscut: " + currentOperator);
                return new TreeSet<>();
        }
    }

    @PostConstruct
    private void initInverseIndex() {
        this.inverseIndex = indexUtils.readInverseIndex(fileUtils.INVERSE_INDEX_PATH);
        System.out.println("Inverse index initialized.");
    }


}
