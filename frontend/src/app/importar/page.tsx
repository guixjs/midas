"use client";

import "./import.css"




export default function importarCsv() {
    return (
        <div className="tela">
      
      <div>
      
      </div>
      
        <div className="cabecalho">
          <img src="imgs/MIDA$NOME.png" alt="" width={250}/>
             <nav className="menu">
          <span className="item">Dashboard</span>
          <span className="ativo">Transações</span>
          <span className="item">Recorrentes</span>
          <span className="item">Categorias</span>
          <span className="item">Contas</span>
        </nav>
        </div>
     
  
      <main className="conteudo">
        <div className="cartao">
          <div className="area-upload">
            <h2 className="titulo">Carregue seu extrato bancário</h2>
            <p className="descricao">Arraste e solte seu extrato bancário CSV aqui </p>
             <p className="descricao">ou</p>
            <label className="botao-upload">
              <span>Selecione arquivo</span>
              <input type="file" accept=".csv" className="hidden" />
            </label>
            <p className="info">Formatos suportados: <strong>.CSV</strong>.</p>
            <p className="subinfo">Importe suas transações bancárias carregando seu arquivo CSV.</p>
          </div>

          <div className="botoes">
            <button className="botao">Voltar</button>
            <button className="botao">Nova Transação</button>
          </div>
        </div>
      </main>
    </div>
  );
}