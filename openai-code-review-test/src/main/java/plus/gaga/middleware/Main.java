package plus.gaga.middleware;

import plus.gaga.middleware.sdk.types.utils.BearerTokenUtils;

public class Main {
    public static void main(String[] args) {
        String token = BearerTokenUtils.getToken("d4946eef67084eb29162a4c4f7c24961.rKibnmDHtCne2ScF");
        System.out.println("token"+ token);
    }
}