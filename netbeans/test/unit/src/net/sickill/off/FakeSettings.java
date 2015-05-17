package net.sickill.off;

import java.util.HashMap;
import java.util.Map;
import net.sickill.off.common.Settings;

/**
 * @author sickill
 */
class FakeSettings extends Settings {

  Map<String, String> stringValues = new HashMap<>();
  Map<String, Boolean> boolValues = new HashMap<>();
  Map<String, Integer> intValues = new HashMap<>();
  Map<String, Float> floatValues = new HashMap<>();

  @Override
  public void setBoolean(String prop, boolean b) {
    boolValues.put(prop, b);
  }

  @Override
  public boolean getBoolean(String prop, boolean def) {
    return boolValues.containsKey(prop) ? boolValues.get(prop) : def;
  }

  @Override
  public void setString(String prop, String s) {
    stringValues.put(prop, s);
  }

  @Override
  public String getString(String prop, String def) {
    return stringValues.containsKey(prop) ? stringValues.get(prop) : def;
  }

  @Override
  public void setInt(String prop, int i) {
    intValues.put(prop, i);
  }

  @Override
  public int getInt(String prop, int def) {
    return intValues.containsKey(prop) ? intValues.get(prop) : def;
  }

  @Override
  public void setFloat(String prop, float f) {
    floatValues.put(prop, f);
  }

  @Override
  public float getFloat(String prop, float def) {
    return floatValues.containsKey(prop) ? floatValues.get(prop) : def;
  }

}
