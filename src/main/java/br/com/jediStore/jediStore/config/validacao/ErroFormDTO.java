package br.com.jediStore.jediStore.config.validacao;

public class ErroFormDTO {
	
	private String campo;
	private String erro;
	
	
	public ErroFormDTO(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}


	public String getCampo() {
		return campo;
	}


	public String getErro() {
		return erro;
	}
	
	
	
}
