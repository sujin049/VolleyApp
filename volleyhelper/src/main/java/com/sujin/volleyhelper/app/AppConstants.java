/**
 * AppConstants.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyhelper
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.app;

/**
 * Created by sujun.n on 31/07/17.
 */
public class AppConstants {

    /**
     * Encodings
     */
    public static final String UTF_8_ENCODING = "UTF-8";

    // Default network time out in milliseconds
    public static final int SOCKET_TIMEOUT_MS = 30 * 1000;

    // Default maximum disk usage in bytes
    public static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;

    // Default cache folder name
    public static final String DEFAULT_CACHE_DIR = "images";

    // API Constants
    public static final String MSG_SERVER_UNREACHABLE = "{'status':'error','message':'Unable to connect to Server'}";

}
