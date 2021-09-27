package coup;

public enum ActionType {
  INCOME,
  FOREIGN_AID,
  TAX,
  STEAL,
  ASSASSINATE,
  EXCHANGE,
  COUP;

  public int iskRequired() {
    return switch (this) {
      case INCOME, FOREIGN_AID, TAX, STEAL, EXCHANGE -> 0;
      case ASSASSINATE -> 3;
      case COUP -> 7;
    };
  }
}
