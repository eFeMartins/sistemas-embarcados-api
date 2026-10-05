package br.edu.uva.sistemasembarcados.services.execptions;

public class DuplicateResourceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DuplicateResourceException(String msg) {
		super(msg);
	}
}
