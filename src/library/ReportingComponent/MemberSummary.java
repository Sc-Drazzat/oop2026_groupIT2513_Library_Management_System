package library.ReportingComponent;

public class MemberSummary {
    private final int memberId;
    private final String memberName;
    private final int totalLoans;
    private final int activeLoans;
    private final double totalFines;

    public MemberSummary(Builder builder) {
        this.memberId = builder.memberId;
        this.memberName = builder.memberName;
        this.totalLoans = builder.totalLoans;
        this.activeLoans = builder.activeLoans;
        this.totalFines = builder.totalFines;
    }
    public int getMemberId() {
        return memberId;
    }
    public String getMemberName() {
        return memberName;
    }
    public int getTotalLoans() {
        return totalLoans;
    }
    public int getActiveLoans() {
        return activeLoans;
    }
    public double getTotalFines() {
        return totalFines;
    }

    @Override
    public String toString() {
        return "MemberSummary{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", totalLoans=" + totalLoans +
                ", activeLoans=" + activeLoans +
                ", totalFines=" + totalFines +
                '}';
    }

    public static class Builder {
        private int memberId;
        private String memberName;
        private int totalLoans;
        private int activeLoans;
        private double totalFines;

        public Builder setMemberId(int memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder setMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public Builder setTotalLoans(int totalLoans) {
            this.totalLoans = totalLoans;
            return this;
        }

        public Builder setActiveLoans(int activeLoans) {
            this.activeLoans = activeLoans;
            return this;
        }

        public Builder setTotalFines(double totalFines) {
            this.totalFines = totalFines;
            return this;
        }

        public MemberSummary build() {
            return new MemberSummary(this);
        }
    }
}
