public class Exemplos{

  private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    private final String BASE_URL = "http://10.1.1.146:8080/api/";
    
    
    //Retorna um cliente com o id preenchido. ou null
     public Cliente addCliente(Cliente cliente){

        ApiService<Cliente> c = new ApiService<Cliente>(Cliente.class)
                .baseUrl(BASE_URL)
                .endPoint("cliente")
                .build();

        return c.add(cliente);
    }
  
  public Cliente login(String telefone, String senha){

        List<Condicao> condicoes = new ArrayList<>();
        condicoes.add(new Condicao("celular", "eq", telefone));
        condicoes.add(new Condicao("senha", "eq", senha));

        ApiService<Cliente> slide = new ApiService<Cliente>(Cliente.class)
                .baseUrl(BASE_URL)
                .endPoint("cliente")
                .condition(condicoes)
                .build();

        Model<Cliente> slides = slide.getAll();

        String g = gson.toJson(slides.objects);
        List<Cliente> slideList = gson.fromJson(g, new TypeToken<List<Cliente>>(){}.getType());

        slides.objects = slideList;

        if(slides.objects.size()>0) {
            return slides.objects.get(0);
        }else{
            return null;
        }
    }
    
    
     public List<ItemCardapio> getAll(){
        ApiService<ItemCardapio> item = new ApiService<ItemCardapio>(ItemCardapio.class)
                .baseUrl(BASE_URL)
                .endPoint("item_cardapio")
                .build();

        Model<ItemCardapio> itens = item.getAll();


      //converte o retorno para uma string json
        String g = gson.toJson(itens.objects);
        serializa os objetos para uma lista do objeto desejado
        List<ItemCardapio> itemList = gson.fromJson(g, new TypeToken<List<ItemCardapio>>(){}.getType());

        itens.objects = itemList;

        return itens.objects;
    }

}
