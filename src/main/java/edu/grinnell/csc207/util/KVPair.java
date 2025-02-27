package main.java.edu.grinnell.csc207.util;

/**
 * An easy way to store key/value pairs.  We assume that other
 * classes will access fields directly.
 *
 * @param <K>
 *   The type of the keys.
 * @param <V>
 *   The type of the values.
 *
 * @author Samuel A. Rebelsky
 */
class KVPair<K, V> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key.
   */
  K key;

  /**
   * The value.
   */
  V val;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty key/value pair.
   */
  KVPair() {
    this(null, null);
  } // KVPair()

  /**
   * Create a new key/value pair.
   *
   * @param pairKey
   *   The key of the new pair.
   * @param pairValue
   *   The value of the new pair.
   */
  KVPair(K pairKey, V pairValue) {
    this.key = pairKey;
    this.val = pairValue;
  } // KVPair(K,V)

  // +------------------+--------------------------------------------
  // | Standard methods |
  // +------------------+

  /**
   * Make a copy of this key/value pair.
   *
   * @return the copy.
   */
  public KVPair<K, V> clone() {
    return new KVPair<K, V>(this.key, this.val);
  } // clone()

  /**
   * Convert the key/value pair to a string (e.g., for printing).
   *
   * @return a string of the form "key:value".
   */
  public String toString() {
    if (null == this.val) {
      return this.key.toString() + ":" + "<null>";
    } else {
      return this.key.toString() + ":" + this.val.toString();
    } // if
  } // toString()

} // class KVPair