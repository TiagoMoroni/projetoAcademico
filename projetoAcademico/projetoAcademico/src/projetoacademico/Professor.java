package ProjetoAcademico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author coelho
 */
public class Professor implements Serializable, Comparable<Professor> {

    private String nome;
    private ArrayList<String> areas;
    private long siape;

    public Professor() {

    }

    public boolean novaArea(String area) {
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i) == null) {
                areas.set(i, area);
                return true;
            }
        }
        return false;
    }
    
    public boolean removerArea(String area) {
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i) != null && areas.get(i).equals(area)) {
                areas.set(i, null);
                return true;
            }
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<String> areas) {
        this.areas = areas;
    }

    public long getSiape() {
        return siape;
    }

    public void setSiape(long siape) {
        this.siape = siape;
    }

    public Professor(String nome, long siape) {
        this.nome = nome;
        this.siape = siape;
    }

    public Professor(String nome, long siape, ArrayList<String> areas) {
        this.nome = nome;
        this.areas = areas;
        this.siape = siape;
    }
    
    public Professor(String nome, long siape, int numeroAreas) {
        this.nome = nome;
        this.siape = siape;
        this.areas = new ArrayList();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + (int) (this.siape ^ (this.siape >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Professor other = (Professor) obj;
        if (this.siape != other.siape) {
            return false;
        }
        return Objects.equals(this.nome, other.nome);
    }

    @Override
    public String toString() {
        String nomes_areas = "";

        for (String d : areas) {
            nomes_areas += d + "\n";
        }
        return "\n nome: " 
                + nome 
                + "\n siape: " 
                + siape
                + "\n areas: "
                + nomes_areas;
    }

    @Override
    public int compareTo(Professor other) {
        Long mat1 = this.siape;
        Long mat2 = other.siape;
        return this.nome.compareTo(other.nome) + mat1.compareTo(mat2);
    }
    
    
}
