/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.wpeace.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-16")
public class People implements org.apache.thrift.TBase<People, People._Fields>, java.io.Serializable, Cloneable, Comparable<People> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("People");

  private static final org.apache.thrift.protocol.TField NAEM_FIELD_DESC = new org.apache.thrift.protocol.TField("naem", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField AGE_FIELD_DESC = new org.apache.thrift.protocol.TField("age", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField SEX_FIELD_DESC = new org.apache.thrift.protocol.TField("sex", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PeopleStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PeopleTupleSchemeFactory());
  }

  public String naem; // required
  public int age; // required
  public String sex; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAEM((short)1, "naem"),
    AGE((short)2, "age"),
    SEX((short)3, "sex");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NAEM
          return NAEM;
        case 2: // AGE
          return AGE;
        case 3: // SEX
          return SEX;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __AGE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAEM, new org.apache.thrift.meta_data.FieldMetaData("naem", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGE, new org.apache.thrift.meta_data.FieldMetaData("age", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SEX, new org.apache.thrift.meta_data.FieldMetaData("sex", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(People.class, metaDataMap);
  }

  public People() {
  }

  public People(
    String naem,
    int age,
    String sex)
  {
    this();
    this.naem = naem;
    this.age = age;
    setAgeIsSet(true);
    this.sex = sex;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public People(People other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetNaem()) {
      this.naem = other.naem;
    }
    this.age = other.age;
    if (other.isSetSex()) {
      this.sex = other.sex;
    }
  }

  public People deepCopy() {
    return new People(this);
  }

  @Override
  public void clear() {
    this.naem = null;
    setAgeIsSet(false);
    this.age = 0;
    this.sex = null;
  }

  public String getNaem() {
    return this.naem;
  }

  public People setNaem(String naem) {
    this.naem = naem;
    return this;
  }

  public void unsetNaem() {
    this.naem = null;
  }

  /** Returns true if field naem is set (has been assigned a value) and false otherwise */
  public boolean isSetNaem() {
    return this.naem != null;
  }

  public void setNaemIsSet(boolean value) {
    if (!value) {
      this.naem = null;
    }
  }

  public int getAge() {
    return this.age;
  }

  public People setAge(int age) {
    this.age = age;
    setAgeIsSet(true);
    return this;
  }

  public void unsetAge() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AGE_ISSET_ID);
  }

  /** Returns true if field age is set (has been assigned a value) and false otherwise */
  public boolean isSetAge() {
    return EncodingUtils.testBit(__isset_bitfield, __AGE_ISSET_ID);
  }

  public void setAgeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AGE_ISSET_ID, value);
  }

  public String getSex() {
    return this.sex;
  }

  public People setSex(String sex) {
    this.sex = sex;
    return this;
  }

  public void unsetSex() {
    this.sex = null;
  }

  /** Returns true if field sex is set (has been assigned a value) and false otherwise */
  public boolean isSetSex() {
    return this.sex != null;
  }

  public void setSexIsSet(boolean value) {
    if (!value) {
      this.sex = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAEM:
      if (value == null) {
        unsetNaem();
      } else {
        setNaem((String)value);
      }
      break;

    case AGE:
      if (value == null) {
        unsetAge();
      } else {
        setAge((Integer)value);
      }
      break;

    case SEX:
      if (value == null) {
        unsetSex();
      } else {
        setSex((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAEM:
      return getNaem();

    case AGE:
      return getAge();

    case SEX:
      return getSex();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAEM:
      return isSetNaem();
    case AGE:
      return isSetAge();
    case SEX:
      return isSetSex();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof People)
      return this.equals((People)that);
    return false;
  }

  public boolean equals(People that) {
    if (that == null)
      return false;

    boolean this_present_naem = true && this.isSetNaem();
    boolean that_present_naem = true && that.isSetNaem();
    if (this_present_naem || that_present_naem) {
      if (!(this_present_naem && that_present_naem))
        return false;
      if (!this.naem.equals(that.naem))
        return false;
    }

    boolean this_present_age = true;
    boolean that_present_age = true;
    if (this_present_age || that_present_age) {
      if (!(this_present_age && that_present_age))
        return false;
      if (this.age != that.age)
        return false;
    }

    boolean this_present_sex = true && this.isSetSex();
    boolean that_present_sex = true && that.isSetSex();
    if (this_present_sex || that_present_sex) {
      if (!(this_present_sex && that_present_sex))
        return false;
      if (!this.sex.equals(that.sex))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_naem = true && (isSetNaem());
    list.add(present_naem);
    if (present_naem)
      list.add(naem);

    boolean present_age = true;
    list.add(present_age);
    if (present_age)
      list.add(age);

    boolean present_sex = true && (isSetSex());
    list.add(present_sex);
    if (present_sex)
      list.add(sex);

    return list.hashCode();
  }

  @Override
  public int compareTo(People other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetNaem()).compareTo(other.isSetNaem());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNaem()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.naem, other.naem);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAge()).compareTo(other.isSetAge());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAge()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.age, other.age);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSex()).compareTo(other.isSetSex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sex, other.sex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("People(");
    boolean first = true;

    sb.append("naem:");
    if (this.naem == null) {
      sb.append("null");
    } else {
      sb.append(this.naem);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("age:");
    sb.append(this.age);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sex:");
    if (this.sex == null) {
      sb.append("null");
    } else {
      sb.append(this.sex);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (naem == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'naem' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'age' because it's a primitive and you chose the non-beans generator.
    if (sex == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'sex' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PeopleStandardSchemeFactory implements SchemeFactory {
    public PeopleStandardScheme getScheme() {
      return new PeopleStandardScheme();
    }
  }

  private static class PeopleStandardScheme extends StandardScheme<People> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, People struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAEM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.naem = iprot.readString();
              struct.setNaemIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // AGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.age = iprot.readI32();
              struct.setAgeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SEX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sex = iprot.readString();
              struct.setSexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetAge()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'age' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, People struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.naem != null) {
        oprot.writeFieldBegin(NAEM_FIELD_DESC);
        oprot.writeString(struct.naem);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(AGE_FIELD_DESC);
      oprot.writeI32(struct.age);
      oprot.writeFieldEnd();
      if (struct.sex != null) {
        oprot.writeFieldBegin(SEX_FIELD_DESC);
        oprot.writeString(struct.sex);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PeopleTupleSchemeFactory implements SchemeFactory {
    public PeopleTupleScheme getScheme() {
      return new PeopleTupleScheme();
    }
  }

  private static class PeopleTupleScheme extends TupleScheme<People> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, People struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.naem);
      oprot.writeI32(struct.age);
      oprot.writeString(struct.sex);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, People struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.naem = iprot.readString();
      struct.setNaemIsSet(true);
      struct.age = iprot.readI32();
      struct.setAgeIsSet(true);
      struct.sex = iprot.readString();
      struct.setSexIsSet(true);
    }
  }

}

