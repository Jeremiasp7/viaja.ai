import React, { useEffect, useState, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import {
  FaHome,
  FaSuitcase,
  FaHeart,
  FaCog,
  FaSignOutAlt,
  FaTrashAlt,
  FaUser,
  FaEnvelope,
  FaLock,
  FaGlobe,
} from "react-icons/fa";
import { Context } from "../../context/AuthContext";
import {
  getUserPreferences,
  updateUserPreferences,
} from "../../services/userPreferences";
import { getSuggestionByPreferences } from "../../services/sugestaoRoteiros";
import { updateUser, deleteUser } from "../../services/usuario";
import "./index.css";

export default function Settings() {
  const { user, authenticated, handleLogout } = useContext(Context);
  const userId = user?.id;
  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname || "/";
  const isActive = (p) => path === p || path.startsWith(p + "/");

  // ---- Estados Gerais ----
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [suggestion, setSuggestion] = useState("");
  const [showPasswordFields, setShowPasswordFields] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  // ---- Dados do Usuário ----
  const [username, setUsername] = useState(user?.nome || "");
  const [email, setEmail] = useState(user?.email || "");
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

  // ---- Preferências ----
  const [form, setForm] = useState({
    estiloDeViagem: "",
    preferenciaDeAcomodacao: "",
    preferenciaDeClima: "",
    faixaOrcamentaria: "",
    duracaoDaViagem: "",
    companhiaDeViagem: "",
    preferenciaDeDatas: [],
  });
  const [dateInput, setDateInput] = useState("");

  useEffect(() => {
    async function fetchPreferences() {
      if (!authenticated || !userId) return;
      try {
        const resp = await getUserPreferences(userId);
        if (resp.data) {
          setForm({
            estiloDeViagem: resp.data.estiloDeViagem || "",
            preferenciaDeAcomodacao: resp.data.preferenciaDeAcomodacao || "",
            preferenciaDeClima: resp.data.preferenciaDeClima || "",
            faixaOrcamentaria: resp.data.faixaOrcamentaria || "",
            duracaoDaViagem: resp.data.duracaoDaViagem || "",
            companhiaDeViagem: resp.data.companhiaDeViagem || "",
            preferenciaDeDatas: resp.data.preferenciaDeDatas || [],
          });
        }
      } catch {
        console.warn("Nenhuma preferência encontrada ainda");
      }
    }
    fetchPreferences();
  }, [authenticated, userId]);

  // ---- Manipulação de Inputs ----
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const addDate = () => {
    if (!dateInput) return;
    setForm((f) => ({
      ...f,
      preferenciaDeDatas: [...f.preferenciaDeDatas, dateInput],
    }));
    setDateInput("");
  };

  const removeDate = (idx) => {
    setForm((f) => ({
      ...f,
      preferenciaDeDatas: f.preferenciaDeDatas.filter((_, i) => i !== idx),
    }));
  };

  // ---- Atualizar Preferências ----
  const handleSave = async (e) => {
    e.preventDefault();
    if (!userId) return setError("Usuário não autenticado");
    setSaving(true);
    setError(null);
    try {
      const payload = {
        ...form,
        duracaoDaViagem: form.duracaoDaViagem
          ? Number(form.duracaoDaViagem)
          : null,
      };
      await updateUserPreferences(userId, payload);
      alert("Preferências salvas com sucesso!");
    } catch (err) {
      console.error("Erro ao salvar preferências", err);
      setError(err.response?.data || err.message || "Erro ao salvar preferências");
    } finally {
      setSaving(false);
    }
  };

  // ---- Gerar Sugestão ----
  const handleGenerateSuggestion = async () => {
    if (!userId) return setError("Usuário não autenticado");
    setLoading(true);
    setSuggestion("");
    setError(null);
    try {
      const resp = await getSuggestionByPreferences(userId);
      setSuggestion(resp.data || String(resp));
    } catch (err) {
      console.error("Erro ao gerar sugestão", err);
      setError(err.response?.data || err.message || "Erro ao gerar sugestão");
    } finally {
      setLoading(false);
    }
  };

  // ---- Alterar Dados da Conta ----
  const handleChangePassword = async () => {
    try {
      await updateUser(userId, {
        nome: username,
        email,
        senhaAntiga: oldPassword,
        novaSenha: newPassword,
      });
      alert("Dados alterados com sucesso!");
      setShowPasswordFields(false);
    } catch (error) {
      const msg = error.response?.data || "Erro ao alterar os dados.";
      alert(msg);
    }
  };

  // ---- Excluir Conta ----
  const handleDeleteAccount = async () => {
    try {
      await deleteUser(userId);
      alert("Conta excluída com sucesso.");
      handleLogout();
    } catch (error) {
      const msg = error.response?.data || "Erro ao excluir a conta.";
      alert(msg);
    }
  };

  // ---- Render ----
  return (
    <>
      <div className="settings-page container">
        <h2><FaGlobe /> Ajustes e Preferências</h2>
        <div className="settings-grid">
          <form className="prefs-form" onSubmit={handleSave}>
            {/* ---- Conta ---- */}
            <h3><FaUser /> Conta</h3>
            {showPasswordFields ? (
              <>
                <label>
                  Nome
                  <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                </label>
                <label>
                  <FaEnvelope /> E-mail
                  <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </label>
                <label>
                  Senha atual
                  <input
                    type="password"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                  />
                </label>
                <label>
                  Nova senha
                  <input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                  />
                </label>
                <div className="form-actions-inline">
                  <button type="button" className="btn-secondary" onClick={handleChangePassword}>
                    Salvar
                  </button>
                  <button
                    type="button"
                    className="btn-secondary"
                    onClick={() => setShowPasswordFields(false)}
                  >
                    Cancelar
                  </button>
                </div>
              </>
            ) : (
              <button
                type="button"
                className="btn-secondary"
                onClick={() => setShowPasswordFields(true)}
              >
                <FaLock /> Alterar informações da conta
              </button>
            )}

            {/* ---- Preferências ---- */}
            <h3><FaGlobe /> Preferências de Viagem</h3>

            <label>
              Estilo de Viagem
              <input
                name="estiloDeViagem"
                value={form.estiloDeViagem}
                onChange={handleChange}
                placeholder="Ex: Aventura, Relax"
              />
            </label>

            <label>
              Preferência de Acomodação
              <input
                name="preferenciaDeAcomodacao"
                value={form.preferenciaDeAcomodacao}
                onChange={handleChange}
                placeholder="Ex: Hotel, Hostel, Airbnb"
              />
            </label>

            <label>
              Clima Preferido
              <input
                name="preferenciaDeClima"
                value={form.preferenciaDeClima}
                onChange={handleChange}
                placeholder="Ex: Quente, Frio"
              />
            </label>

            <label>
              Faixa Orçamentária
              <input
                name="faixaOrcamentaria"
                value={form.faixaOrcamentaria}
                onChange={handleChange}
                placeholder="Ex: 2000-5000"
              />
            </label>

            <label>
              Duração da Viagem (dias)
              <input
                name="duracaoDaViagem"
                type="number"
                min="1"
                value={form.duracaoDaViagem}
                onChange={handleChange}
              />
            </label>

            <label>
              Companhia de Viagem
              <input
                name="companhiaDeViagem"
                value={form.companhiaDeViagem}
                onChange={handleChange}
                placeholder="Ex: Amigos, Família..."
              />
            </label>

            <div className="dates-block">
              <label>Datas Preferidas</label>
              <div className="dates-input-row">
                <input
                  type="date"
                  value={dateInput}
                  onChange={(e) => setDateInput(e.target.value)}
                />
                <button type="button" className="btn-secondary" onClick={addDate}>
                  Adicionar
                </button>
              </div>
              <ul className="dates-list">
                {form.preferenciaDeDatas.map((d, i) => (
                  <li key={i}>
                    {d}{" "}
                    <button type="button" className="btn-secondary" onClick={() => removeDate(i)}>
                      Remover
                    </button>
                  </li>
                ))}
              </ul>
            </div>

            {error && <div className="error">{String(error)}</div>}

            <div className="form-actions">
              <button type="submit" disabled={saving}>
                {saving ? "Salvando..." : "Salvar Preferências"}
              </button>
            </div>

            {/* ---- Sessão ---- */}
            <h3><FaSignOutAlt /> Sessão</h3>
            <button type="button" className="btn-secondary" onClick={handleLogout}>
              <FaSignOutAlt /> Sair da conta
            </button>
            <button
              type="button"
              className="btn-secondary"
              onClick={() => setShowDeleteModal(true)}
            >
              <FaTrashAlt /> Excluir conta
            </button>
          </form>

          {/* ---- Sugestão de Roteiro ---- */}
          <aside className="prefs-suggestion">
            <h3>Sugestão de Roteiro (por Preferências)</h3>
            <p>Gere uma sugestão baseada nas suas preferências salvas.</p>
            <div className="suggestion-actions">
              <button
                className="buttonSubmit"
                onClick={handleGenerateSuggestion}
                disabled={loading}
              >
                {loading ? "Gerando..." : "Gerar sugestão"}
              </button>
            </div>
            <div className="suggestion-result">
              {suggestion ? (
                <pre className="result-pre">{suggestion}</pre>
              ) : (
                <div className="hint">Nenhuma sugestão gerada ainda.</div>
              )}
            </div>
          </aside>
        </div>
      </div>

      {/* ---- Modal de exclusão ---- */}
      {showDeleteModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Confirmação de exclusão</h3>
            <p>Tem certeza que deseja excluir sua conta? Esta ação é irreversível.</p>
            <div className="modal-buttons">
              <button className="btn-danger" onClick={handleDeleteAccount}>
                Excluir
              </button>
              <button
                className="btn-secondary"
                onClick={() => setShowDeleteModal(false)}
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ---- Rodapé ---- */}
      <footer className="app-footer">
        <div
          className={`footer-icon ${isActive("/dashboard") ? "active" : ""}`}
          onClick={() => navigate("/dashboard")}
        >
          <FaHome />
          <p>Início</p>
        </div>
        <div
          className={`footer-icon ${isActive("/roteiros") ? "active" : ""}`}
          onClick={() => navigate("/roteiros")}
        >
          <FaSuitcase />
          <p>Roteiros</p>
        </div>
        <div
          className={`footer-icon ${isActive("/favoritos") ? "active" : ""}`}
        >
          <FaHeart />
          <p>Favoritos</p>
        </div>
        <div
          className={`footer-icon ${isActive("/ajustes") ? "active" : ""}`}
          onClick={() => navigate("/ajustes")}
          title="Preferências"
        >
          <FaCog />
          <p>Preferências</p>
        </div>
      </footer>
    </>
  );
}
