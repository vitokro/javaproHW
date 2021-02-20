package ua.kiev.prog.db.entity;


public enum Currency {
    UAH,
    USD,
    EUR;

    public static Currency getCurrency(String cur){
        switch (cur.toLowerCase()){
            case "uah": return UAH;
            case "usd": return USD;
            case "eur": return EUR;
            default: return null;
        }
    }

}
