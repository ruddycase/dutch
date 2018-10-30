package com.dtt.handler.model;

public enum ReportStatus {
    PROGRESS {
        @Override
        public boolean isProgress() {
            return true;
        }
    },
    SUBMITTED {
        @Override
        public boolean isSubmitted() {
            return true;
        }
    },
    APPROVED {
        @Override
        public boolean isApproved() {
            return true;
        }
    },
    REJECTED {
        @Override
        public boolean isRejected() {
            return true;
        }
    },
    DEFAULT {
        @Override
        public boolean isDefault() {
            return true;
        }
    };

    public boolean isProgress() {return false;}

    public boolean isSubmitted() {return false;}

    public boolean isApproved() {return false;}
    
    public boolean isRejected() {return false;}
    
    public boolean isDefault() {return false;}

}
