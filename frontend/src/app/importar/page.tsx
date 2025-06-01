"use client";

import { api } from "@/services/api";
import "./import.css";
import { useState } from 'react';

export default function ImportarCsv() {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [uploadStatus, setUploadStatus] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      setSelectedFile(event.target.files[0]);
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!selectedFile) {
      setUploadStatus('Por favor, selecione um arquivo.');
      return;
    }

    setIsLoading(true);
    setUploadStatus(null);

    try {
      const formData = new FormData();
      formData.append('file', selectedFile);

      await api.postArquivo('/transaction/import', formData);

      setUploadStatus('Arquivo CSV importado com sucesso!');
      setSelectedFile(null);

      const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
      if (fileInput) fileInput.value = '';
    } catch (error) {
      console.error('Erro ao importar CSV:', error);
      setUploadStatus(`Erro ao importar CSV: ${error instanceof Error ? error.message : 'Erro desconhecido'}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="dashboard">
      <nav className="navbar">
        <div className="logo-container">
          <img src="imgs/MIDA$NOME.svg" alt="Midas" className="logo" />
        </div>
        
        <div className="nav-links">
          <a href="/dashboard" className="nav-link">Dashboard</a>
          <a href="/transacoes" className="nav-link">Transações</a>
          <a href="/recorrentes" className="nav-link">Recorrentes</a>
          <a href="/categorias" className="nav-link">Categorias</a>
          <a href="/contas" className="nav-link">Contas</a>
        </div>
        
        <div className="user-profile">
          <div className="user-icon">
            <span></span>
          </div>
        </div>
      </nav>

      <main className="conteudo">
        <div className="cartao">
          <form onSubmit={handleSubmit} className="area-upload">
            <h2 className="titulo">Carregue seu extrato bancário</h2>
            <p className="descricao">Arraste e solte seu extrato bancário CSV aqui </p>
            <p className="descricao">ou</p>
            <label className="botao-upload">
              <span>Selecione arquivo</span>
              <input 
                type="file" 
                accept=".csv" 
                className="hidden" 
                onChange={handleFileChange}
              />
            </label>
            {selectedFile && (
              <p className="info">Arquivo selecionado: <strong>{selectedFile.name}</strong></p>
            )}
            <p className="info">Formatos suportados: <strong>.CSV</strong>.</p>
            <p className="subinfo">Importe suas transações bancárias carregando seu arquivo CSV.</p>
            
            {uploadStatus && (
              <p className={`status ${uploadStatus.includes('sucesso') ? 'success' : 'error'}`}>
                {uploadStatus}
              </p>
            )}
            
            {selectedFile && (
              <button 
                type="submit" 
                className="botao-upload"
                disabled={isLoading}
              >
                {isLoading ? 'Enviando...' : 'Enviar Arquivo'}
              </button>
            )}
          </form>

          <div className="botoes">
            <a href="/transacoes" className="botao">Voltar</a>
            
          </div>
        </div>
      </main>
    </div>
  );
}