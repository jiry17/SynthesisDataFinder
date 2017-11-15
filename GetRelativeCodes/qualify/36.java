boolean isIPv4Address(String inputString) {
  String[] ar = inputString.split("\\.");
  int countGood = 0;
  for(String s : ar) {
    if (s.isEmpty()) return false;
    if (isNumeric(s) && Integer.parseInt(s) <= 255) countGood ++;
  }
  return countGood == 4;
}

boolean isNumeric(String str) {
  for (char c : str.toCharArray()) {
    if (!Character.isDigit(c)) return false;
  }
  return true;
}
