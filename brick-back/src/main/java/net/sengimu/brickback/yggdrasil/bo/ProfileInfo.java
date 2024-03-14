package net.sengimu.brickback.yggdrasil.bo;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.po.Texture;
import net.sengimu.brickback.utils.KeyPairUtil;

import java.util.*;

@Data
public class ProfileInfo {

    private String id;
    private String name;
    private List<Map<String, Object>> properties;

    public ProfileInfo(Profile profile, List<Texture> textures, String urlPrefix) {

        this.id = profile.getUuid();
        this.name = profile.getName();

        if (textures != null && !textures.isEmpty()) {

            List<Map<String, Object>> properties = new ArrayList<>();

            Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, KeyPairUtil.getPrivateKeyBase64(), KeyPairUtil.getPublicKeyBase64());

            Map<String, Object> property1 = new HashMap<>();
            property1.put("name", "textures");
            String value = Base64.encode(JSONUtil.toJsonStr(new TextureInfo(textures, urlPrefix)));
            property1.put("value", value);
            property1.put("signature", Base64.encode(sign.sign(value)));
            properties.add(property1);

            Map<String, Object> property2 = new HashMap<>();
            property2.put("name", "uploadableTextures");
            property2.put("value", profile.getAllowUpload());
            property2.put("signature", Base64.encode(sign.sign(profile.getAllowUpload())));
            properties.add(property2);

            this.properties = properties;
        }
    }

    @Data
    class TextureInfo {

        private long timestamp;
        private String profileId;
        private String profileName;
        private Map<String, Object> textures;

        public TextureInfo(List<Texture> textures, String urlPrefix) {

            this.timestamp = new Date().getTime();
            this.profileId = id;
            this.profileName = name;

            Map<String, Object> tempTextures = new HashMap<>();
            for (Texture texture : textures) {

                Map<String, Object> typeMap = new HashMap<>();
                typeMap.put("url", urlPrefix + texture.getHashPath() + ".png");
                typeMap.put("metadata", MapUtil.of("model", texture.getModel()));
                tempTextures.put(texture.getType(), typeMap);
            }
            this.textures = tempTextures;
        }
    }
}
