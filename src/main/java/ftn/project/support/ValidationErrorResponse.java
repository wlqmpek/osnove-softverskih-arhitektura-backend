package ftn.project.support;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return this.violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public String toString() {
        return "ValidationErrorResponse{" + "violations=" + this.violations + '}';
    }
}
