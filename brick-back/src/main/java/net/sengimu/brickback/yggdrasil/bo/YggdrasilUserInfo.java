package net.sengimu.brickback.yggdrasil.bo;

import lombok.Data;
import net.sengimu.brickback.po.User;
import net.sengimu.brickback.po.UserProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class YggdrasilUserInfo {

    private String id;
    private List<Map<String, Object>> properties;

    public YggdrasilUserInfo(User user, List<UserProperty> userProperties) {

        this.id = String.valueOf(user.getId());

        List<Map<String, Object>> propertyInfos = new ArrayList<>();
        if (userProperties != null) {
            for (UserProperty userProperty : userProperties) {

                Map<String, Object> map = new HashMap<>();
                map.put("name", userProperty.getName());
                map.put("value", userProperty.getValue());
                propertyInfos.add(map);
            }
        }
        this.properties = propertyInfos;
    }
}
