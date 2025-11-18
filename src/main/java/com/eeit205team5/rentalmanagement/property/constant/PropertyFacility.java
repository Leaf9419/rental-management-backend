package com.eeit205team5.rentalmanagement.property.constant;

import java.util.EnumSet;
import java.util.Set;

/**
 * 物業設施列舉
 * <p>
 * 使用位元運算來高效儲存多個設施狀態，每個設施佔用一個位元位置。
 * 所有設施可以編碼為單一 long 值，最多支援 64 種設施。
 * </p>
 * 
 * <p>
 * 位元位置分配：
 * </p>
 * <ul>
 * <li>0-9: 家電設備</li>
 * <li>10-17: 傢俱</li>
 * <li>18-25: 生活設施</li>
 * <li>26-31: 安全設備</li>
 * </ul>
 */
public enum PropertyFacility {
    // 家電 APPLIANCES 0~9
    WASHING_MACHINE(0, "洗衣機"),
    REFRIGERATOR(1, "冰箱"),
    AIR_CONDITIONER(2, "冷氣"),
    WATER_HEATER(3, "熱水器"),
    TELEVISION(4, "電視"),
    // 傢俱 FURNITURE 10~17
    BED(10, "床"),
    WARDROBE(11, "衣櫃"),
    DESK(12, "書桌"),
    // 生活設施 UTILITIES 18~25
    INTERNET(18, "網路"),
    NATURAL_GAS(19, "天然氣"),
    // 安全設備 SECURITY 26~31
    FIRE_EXTINGUISHER(26, "滅火器"),
    SMOKE_DETECTOR(27, "煙霧偵測器");

    private final int bitPosition;
    private final String description;

    PropertyFacility(int bitPosition, String description) {
        this.bitPosition = bitPosition;
        this.description = description;
    }

    public int getBitPosition() {
        return bitPosition;
    }

    public long getBitMask() {
        return 1L << bitPosition;
    }

    public String getDescription() {
        return description;
    }

    // 將多個設施轉換為二進位值
    public static long encode(Set<PropertyFacility> facilities) {
        long result = 0L;
        for (PropertyFacility facility : facilities) {
            result = result | facility.getBitMask();
        }
        return result;
    }

    // 從二進位值轉換為設施集合
    public static Set<PropertyFacility> decode(long bitField) {
        Set<PropertyFacility> result = EnumSet.noneOf(PropertyFacility.class);
        for (PropertyFacility facility : values()) {
            if ((bitField & facility.getBitMask()) != 0) {
                result.add(facility);
            }
        }
        return result;
    }

    // 檢查是否包含某設施
    public static boolean contains(long bitField, PropertyFacility facility) {
        return (bitField & facility.getBitMask()) != 0;
    }
}