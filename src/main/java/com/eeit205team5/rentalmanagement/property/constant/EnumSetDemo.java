// package com.eeit205team5.rentalmanagement.property.constant;

// import java.util.EnumSet;
// import java.util.Set;

// public class EnumSetDemo {
// public static void main(String[] args) {
// // // 建立集合
// // Set<PropertyFacility> facilities = EnumSet.noneOf(PropertyFacility.class);

// // // 新增家電
// // facilities.add(PropertyFacility.WASHING_MACHINE);
// // facilities.add(PropertyFacility.REFRIGERATOR);
// // facilities.add(PropertyFacility.AIR_CONDITIONER);

// // long bitField = PropertyFacility.encode(facilities);
// long bitField = 1254023230;
// System.out.println("二進位: " + Long.toBinaryString(bitField));
// Set<PropertyFacility> facilities = PropertyFacility.decode(bitField);
// System.out.println("解碼: " + facilities);
// // 遍歷
// System.out.println("所有設施:");
// for (PropertyFacility f : facilities) {
// System.out.println("- " + f.getDescription());
// }
// long encoded = PropertyFacility.encode(facilities);
// System.out.println("編碼: " + encoded);
// System.out.println("二進位: " + Long.toBinaryString(encoded));
// }
// }
