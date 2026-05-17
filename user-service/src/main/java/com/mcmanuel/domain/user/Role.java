package com.mcmanuel.domain.user;

public enum Role {
    ADMIN,
    USER



//    CIA level structure
//    private int level;
//
//    Role(int setLevel){
//        this.level =setLevel;
//    }
//
//    public void upgrade() throws LimitExceededException {
//        if (level >=getHighestLevel()) {
//            throw new LimitExceededException("level exceeded");
//        }
//        this.level++;
//    }
//
//    public void downgrade() throws LimitExceededException {
//        if (level <=0) {
//            throw new LimitExceededException("lowest level");
//        }
//        this.level --;
//    }
//
//    public int getHighestLevel(){
//        return Role.values().length;
//    }
//
}
