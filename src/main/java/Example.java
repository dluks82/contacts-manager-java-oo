import java.util.List;
import java.util.ArrayList;

class Produto {
    public String codigo;
    public double precoUnitario;
    public int quantidade;

    public Produto(String codigo, double precoUnitario, int quantidade) {
        this.codigo = codigo;
        this.precoUnitario = precoUnitario;
        this.quantidade = Math.max(quantidade, 0);
    }
}

class NotaFiscal {
    private List<Produto> produtos;

    public NotaFiscal() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(String codigo) {
        for (Produto p : produtos) {
            if (p.codigo.equals(codigo)) {
                produtos.remove(p);
                return;
            }
        }
    }

    public double calcularValorProduto(Produto produto) {
        return produto.precoUnitario * produto.quantidade;
    }

    public double calcularValorNotaFiscal() {
        double valorTotal = 0.0;
        for (Produto p : produtos) {
            valorTotal += calcularValorProduto(p);
        }

        return valorTotal;
    }
}

