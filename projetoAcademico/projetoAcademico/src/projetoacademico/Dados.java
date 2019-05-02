/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoAcademico;

import java.io.Serializable;

/**
 *
 * @author 05200244
 */
public class Dados implements Serializable{
    private SetorEnsino ensino;
    private Aluno[]     alunos;

    public SetorEnsino getEnsino() {
        return ensino;
    }

    public void setEnsino(SetorEnsino ensino) {
        this.ensino = ensino;
    }

    public Aluno[] getAlunos() {
        return alunos;
    }

    public void setAlunos(Aluno[] alunos) {
        this.alunos = alunos;
    }

    public Dados(SetorEnsino ensino, Aluno[] alunos) {
        this.ensino = ensino;
        this.alunos = alunos;
    }
    
    
}
