/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ifyou.skypivot.framework.utils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ashvayka on 13.07.17.
 */
public class UUIDConverter {

    public static UUID fromStringToUUID(String src) {
        return UUID.fromString(src.substring(7, 15) + "-" + src.substring(3, 7) + "-1"
                + src.substring(0, 3) + "-" + src.substring(15, 19) + "-" + src.substring(19));
    }

    public static String fromString(String src) {
        return src.substring(7, 15) + "-" + src.substring(3, 7) + "-1"
                + src.substring(0, 3) + "-" + src.substring(15, 19) + "-" + src.substring(19);
    }

    public static UUID fromUUIDString(String UUIdStr) {
        String UUIDSTR = UUIdStr.substring(15, 18) + UUIdStr.substring(9, 13) + UUIdStr.substring(0, 8) + UUIdStr.substring(19, 23) + UUIdStr.substring(24);
        return  fromStringToUUID(UUIDSTR);
    }

    public static List<String> fromStringtoList(List<String> srcs) {
        return srcs.stream().map(UUIDConverter::fromString).collect(Collectors.toList());

    }

    public static String fromTimeUUID(UUID src) {
        String str = src.toString();
        // 58e0a7d7-eebc-11d8-9669-0800200c9a66 => 1d8eebc58e0a7d796690800200c9a66. Note that [11d8] -> [1d8]
        return str.substring(15, 18) + str.substring(9, 13) + str.substring(0, 8) + str.substring(19, 23) + str.substring(24);
    }

    public static List<String> fromTimeUUIDs(List<UUID> uuids) {
        if (uuids == null) {
            return null;
        }
        return uuids.stream().map(UUIDConverter::fromTimeUUID).collect(Collectors.toList());
    }

    public static String fromTimeUUID(String str) {

        return str.substring(15, 18) + str.substring(9, 13) + str.substring(0, 8) + str.substring(19, 23) + str.substring(24);
    }

    public static List<String> fromTimeString(List<String> uuids) {
        if (uuids == null) {
            return null;
        }
        return uuids.stream().map(UUIDConverter::fromTimeUUID).collect(Collectors.toList());
    }

    public static void main(String[] args) {

//        String str = "1ee004ca647021093e5e16365c260de";
//        String id = fromString(str);
//        System.out.println(id);

        String aa = "2d731860-0362-11ee-81ca-671070b2103a";
        String uuid = fromTimeUUID(aa);
        System.out.println(uuid);

//        String timeStamp = Instant.now().getEpochSecond() + "";
//
//        String key ="ifyouAuthentication"+ timeStamp;
//
//        String md5 = DigestUtils.md5DigestAsHex(key.getBytes());
//
//        System.out.println("timeStamp:" + timeStamp);
//        System.out.println("md5:" + md5);



    }



}

