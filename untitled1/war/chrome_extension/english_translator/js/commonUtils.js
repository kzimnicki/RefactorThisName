var EXTENSION_ID = "fkpnojcafafpkoonhhgnapemmbkgphlm";

function log(arguments) {
    if (chrome.i18n.getMessage("@@extension_id") == EXTENSION_ID) {
        console.log(arguments);
    }
}

function base64_encode(data) {
    // use native implementation if it's present
    if (typeof btoa === 'function') return btoa(data)

    var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
        b64a = b64.split(''),
        o1, o2, o3, h1, h2, h3, h4, bits, i = 0,
        ac = 0,
        enc = "",
        tmp_arr = [];

    do { // pack three octets into four hexets
        o1 = data.charCodeAt(i++);
        o2 = data.charCodeAt(i++);
        o3 = data.charCodeAt(i++);

        bits = o1 << 16 | o2 << 8 | o3;

        h1 = bits >> 18 & 0x3f;
        h2 = bits >> 12 & 0x3f;
        h3 = bits >> 6 & 0x3f;
        h4 = bits & 0x3f;

        // use hexets to index into b64, and append result to encoded string
        tmp_arr[ac++] = b64a[h1] + b64a[h2] + b64a[h3] + b64a[h4];
    } while (i < data.length);

    enc = tmp_arr.join('');
    var r = data.length % 3;
    return (r ? enc.slice(0, r - 3) : enc) + '==='.slice(r || 3);
}

