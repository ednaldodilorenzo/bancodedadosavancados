package br.edu.ifpb;

import br.edu.ifpb.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        try(Jedis jedis = new Jedis("10.13.132.43", 6379)) {
            ObjectMapper mapper = new ObjectMapper();
            Usuario usuario = new Usuario("2", "João da Silva", "admin");
            try {
                String usuarioJson = jedis.get("usuario:2");
                if (usuarioJson == null) {
                    System.out.println("Chave não existe ainda.");
                    String json = mapper.writeValueAsString(usuario);
                    jedis.setex("usuario:" + usuario.getId(), 30, json);
                } else {
                    System.out.println("Chave já existe no redis.");
                }

                usuarioJson = jedis.get("usuario:2");
                if (usuarioJson == null) {
                    System.out.println("Usuário não encontrado no Redis.");
                    return;
                }
                Usuario usuarioFromRedis = mapper.readValue(usuarioJson, Usuario.class);
                System.out.println("Usuário recuperado do Redis: " + usuarioFromRedis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}