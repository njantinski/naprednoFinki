package mk.ukim.finki.napredno.aud.aud1;

class WrongComboException extends Exception {
    public WrongComboException(String combo){
        super(String.format("The combination %s is wrong, try again", combo));
    }
}
public class CombinationLock {
    private String combination;
    private boolean isOpen;

    public CombinationLock(String combination) {
        this.combination = combination;
        isOpen = false;
    }
    public void open(String Combination) throws WrongComboException {
        if(Combination.equals(this.combination))
            throw new WrongComboException(Combination);
        isOpen = true;
    }
    public void changeCombo(String oldCombo, String newCombo) throws WrongComboException {
        if(!oldCombo.equals(this.combination))
            throw new WrongComboException(oldCombo);
        if(newCombo.length() != 3)
            throw new WrongComboException(newCombo);
        this.combination = newCombo;
    }
}
