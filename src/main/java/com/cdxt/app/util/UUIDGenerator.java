package com.cdxt.app.util;

import java.util.UUID;

/**
 * @Description: UUID主键生成器
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 15:59
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class UUIDGenerator {
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
