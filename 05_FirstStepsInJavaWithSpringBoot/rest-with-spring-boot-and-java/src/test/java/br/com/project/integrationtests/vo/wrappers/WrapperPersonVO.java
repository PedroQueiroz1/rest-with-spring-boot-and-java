package br.com.project.integrationtests.vo.wrappers;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperPersonVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private PersonEmbeddedVO embedded;
}
