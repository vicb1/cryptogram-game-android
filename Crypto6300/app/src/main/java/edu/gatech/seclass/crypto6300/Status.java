package edu.gatech.seclass.crypto6300;

public enum Status {
    UNSTARTED,
    INPROGRESS,
    COMPLETE;

    public static Status getStatus(String str){
        if(str.equals(UNSTARTED.name())){
            return UNSTARTED;
        } else if (str.equals(INPROGRESS.name())){
            return INPROGRESS;
        } else if (str.equals(COMPLETE.name())){
            return COMPLETE;
        }
        return UNSTARTED;
    }
}
