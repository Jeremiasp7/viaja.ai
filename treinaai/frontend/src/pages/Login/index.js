import React, { useContext, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Context } from "../../context/AuthContext";
import "./index.css";

function Login() {
  const { handleLogin, authenticated, loading } = useContext(Context);
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();
  const [serverError, setServerError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (!loading && authenticated) {
      navigate("/dashboard");
    }
  }, [authenticated, loading, navigate]);

  const onSubmit = async (data) => {
    setServerError("");
    setIsSubmitting(true);
    try {
      await handleLogin(data.email, data.senha);
    } catch (error) {
      console.error('Erro completo:', error);
      setServerError(
        error.response?.data?.message || error.message || "Erro ao fazer login. Verifique suas credenciais."
      );
      setIsSubmitting(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <div className="login-header">
          <h1>TreinaAI</h1>
          <p>Seu assistente de treinos inteligente</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="login-form">
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              placeholder="seu@email.com"
              {...register("email", {
                required: "Email é obrigatório",
                pattern: {
                  value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                  message: "Email inválido",
                },
              })}
            />
            {errors.email && (
              <span className="error-message">{errors.email.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              placeholder="Digite sua senha"
              {...register("senha", {
                required: "Senha é obrigatória",
                minLength: {
                  value: 6,
                  message: "Senha deve ter no mínimo 6 caracteres",
                },
              })}
            />
            {errors.senha && (
              <span className="error-message">{errors.senha.message}</span>
            )}
          </div>

          {serverError && (
            <div className="alert alert-error">{serverError}</div>
          )}

          <button
            type="submit"
            className="btn btn-primary"
            disabled={loading || isSubmitting}
          >
            {isSubmitting ? "Entrando..." : "Entrar"}
          </button>
        </form>

        <div className="login-footer">
          <p>
            Não tem uma conta?{" "}
            <Link to="/register" className="link">
              Registre-se aqui
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Login;
