import Sjcl from 'sjcl';

export default {
    hash: function (bucketName) {
        return Sjcl.codec.hex.fromBits(Sjcl.hash.sha256.hash(bucketName));
    },
    encrypt: function (data, secret) {
        var encryptedObj = Sjcl.encrypt(secret, data);
        var encryptedObjAsJson = JSON.stringify(encryptedObj);
        return Sjcl.codec.base64.fromBits(Sjcl.codec.utf8String.toBits(encryptedObjAsJson));
    },
    decrypt: function (data, secret) {
        var encryptedObjAsJson = Sjcl.codec.utf8String.fromBits(Sjcl.codec.base64.toBits(data));
        var encryptedObj = JSON.parse(encryptedObjAsJson);
        return Sjcl.decrypt(secret, encryptedObj);
    }
}