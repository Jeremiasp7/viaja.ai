import React, { useState, useRef, useEffect } from "react";
import styled from "styled-components";
import { FaPaperPlane, FaPlus, FaStar, FaMapMarkerAlt, FaHome, FaHeart, FaSuitcase, FaCog } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import api from "../../api"

const HomeScreen = styled.div`
  min-height: 100vh;
  background: var(--background-color);
`;

const HomeHeader = styled.header`
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), 
              url('https://images.unsplash.com/photo-1433838552652-f9a46b332c40?fm=jpg&q=60&w=3000') no-repeat center center/cover;
  color: var(--white);
  padding: 4rem 2rem;
  text-align: center;
`;

const HeaderContent = styled.div`
  max-width: 800px;
  margin: 0 auto;
`;

const Container = styled.main`
  padding: 1rem 2rem;
  max-width: 1200px;
  margin: auto auto 50px auto;
`;

const ChatContainer = styled.div`
  background: var(--white);
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin-top: 2rem;
`;

const MessagesContainer = styled.div`
  height: 400px;
  overflow-y: auto;
  padding: 1.5rem;
  background: var(--light-gray);
`;

const Message = styled.div`
  display: flex;
  justify-content: ${props => props.isUser ? 'flex-end' : 'flex-start'};
  margin-bottom: 1rem;
`;

const MessageBubble = styled.div`
  max-width: 70%;
  padding: 1rem 1.5rem;
  border-radius: 1.5rem;
  background: ${props => props.isUser ? 'var(--primary-color)' : 'var(--white)'};
  color: ${props => props.isUser ? 'var(--white)' : 'var(--text-color)'};
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: ${props => !props.isUser ? '1px solid #e2e8f0' : 'none'};
`;

const InputContainer = styled.div`
  display: flex;
  align-items: flex-end; /* Alinha o botão com a base do textarea */
  padding: 1rem; /* Padding ajustado */
  border-top: 1px solid var(--light-gray);
  background: var(--white);
`;

const TextArea = styled.textarea`
  flex: 1;
  padding: 1rem 1.5rem;
  border-radius: 20px; /* Raio de borda mais retangular */
  border: none;
  font-size: 1rem;
  background: var(--light-gray);
  resize: none; /* Impede que o usuário redimensione */
  overflow-y: auto; /* Adiciona scroll se o texto for muito longo */
  max-height: 100px; /* Limita o crescimento (aprox. 5 linhas) */
  font-family: inherit; /* Garante que a fonte seja a mesma do app */
  line-height: 1.5;

  &:focus {
    outline: none;
    background: var(--white);
    box-shadow: 0 0 0 2px var(--primary-color);
  }
`;

const SendButton = styled.button`
  background: var(--primary-color);
  color: var(--white);
  border: none;
  border-radius: 50%; /* Botão circular */
  width: 44px;  /* Tamanho fixo */
  height: 44px; /* Tamanho fixo */
  padding: 0; /* Remove padding interno */
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center; /* Centraliza o ícone */
  margin-left: 0.75rem; /* Margem ajustada */
  flex-shrink: 0; /* Impede que o botão encolha */
  
  &:hover {
    background: #350668;
  }
  
  &:disabled {
    background: #a5a5a5;
    cursor: not-allowed;
  }

  /* dimensiona o ícone dentro do botão */
  svg {
    width: 20px;
    height: 20px;
  }
`;

const RecommendationsSection = styled.section`
  margin-top: 3rem;
`;

const SectionTitle = styled.h3`
  color: var(--text-color);
  margin-bottom: 1.5rem;
`;

const CardsContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 1rem;
`;

const RecommendationCard = styled.div`
  background: var(--white);
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  }
`;

const CardImage = styled.img`
  width: 100%;
  height: 200px;
  object-fit: cover;
`;

const CardInfo = styled.div`
  padding: 1.5rem;
`;

const CardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
`;

const LocationInfo = styled.div`
  flex: 1;
`;

const CityName = styled.h4`
  margin: 0;
  color: var(--text-color);
  font-size: 1.25rem;
`;

const CountryName = styled.p`
  margin: 0.25rem 0 0 0;
  color: #666;
  font-size: 0.9rem;
`;

const AddButton = styled.button`
  background: var(--secondary-color);
  color: var(--white);
  border: none;
  border-radius: 50px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
  font-weight: 600;
  
  &:hover {
    background: #e57a00;
  }
`;

const PlacesList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 1rem 0 0 0;
`;

const PlaceItem = styled.li`
  display: flex;
  align-items: flex-start;
  padding: 0.75rem 0;
  border-bottom: 1px solid var(--light-gray);
  font-size: 0.9rem;
  
  &:last-child {
    border-bottom: none;
  }
`;

const PlaceIcon = styled(FaMapMarkerAlt)`
  color: var(--secondary-color);
  margin-right: 0.75rem;
  margin-top: 0.2rem;
  flex-shrink: 0;
`;

const PlaceInfo = styled.div`
  flex: 1;
`;

const PlaceName = styled.strong`
  color: var(--text-color);
  display: block;
  margin-bottom: 0.25rem;
`;

const Coordinates = styled.div`
  font-size: 0.8rem;
  color: #666;
`;

const AppFooter = styled.footer`
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: var(--white);
  display: flex;
  justify-content: space-around;
  padding: 0.5rem 0;
  box-shadow: 0 -2px 10px rgba(0,0,0,0.1);
`;

const FooterIcon = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 1.2rem;
  color: ${props => props.active ? 'var(--primary-color)' : '#aaa'};
  cursor: pointer;
  
  p {
    font-size: 0.7rem;
    margin: 0.2rem 0 0 0;
  }
`;

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

const Modal = styled.div`
  background: var(--white);
  padding: 2rem;
  border-radius: 1rem;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
`;

const ModalTitle = styled.h3`
  margin-bottom: 1.5rem;
  color: var(--text-color);
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const CheckboxGroup = styled.div`
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin: 1rem 0;
`;

const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  font-weight: 600;
  color: var(--text-color);
`;

const Checkbox = styled.input`
  width: auto;
`;

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Label = styled.label`
  font-weight: 600;
  color: var(--text-color);
`;

const ModalButtons = styled.div`
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1.5rem;
`;

const Button = styled.button`
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  font-weight: 600;
  
  ${({ primary }) => primary ? `
    background: var(--primary-color);
    color: var(--white);
    
    &:hover {
      background: #350668;
    }
  ` : `
    background: var(--light-gray);
    color: var(--text-color);
    
    &:hover {
      background: #d1d1d1;
    }
  `}
`;

const LoadingDots = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.25rem;
  padding: 1rem;
`;

const Dot = styled.div`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--primary-color);
  animation: bounce 1.4s infinite ease-in-out;
  
  &:nth-child(1) { animation-delay: -0.32s; }
  &:nth-child(2) { animation-delay: -0.16s; }
  
  @keyframes bounce {
    0%, 80%, 100% {
      transform: scale(0.8);
      opacity: 0.5;
    }
    40% {
      transform: scale(1);
      opacity: 1;
    }
  }
`;

const RecommendationChatPage = () => {
  const { authenticated, user, loading: authLoading } = useAuth();
  console.log(authenticated);
  console.log(user);
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState("");
  const [recommendations, setRecommendations] = useState([]);
  const [selectedDestination, setSelectedDestination] = useState(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const [isLoading, setLoading] = useState(false);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!inputMessage.trim() || isLoading) return;

    const userMessage = {
      id: Date.now(),
      text: inputMessage,
      isUser: true,
      timestamp: new Date()
    };

    setMessages(prev => [...prev, userMessage]);
    setInputMessage("");
    setLoading(true);
    const payload = {
      "userPrompt": userMessage.text
    }

    try {
      const token = localStorage.getItem("token");
      const response = await api.post(`/recomendacoes/${user.id}`, payload, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.status === 200) {
        const data = response.data;
        
        const botMessage = {
          id: Date.now() + 1,
          text: "Aqui estão algumas recomendações baseadas no seu histórico de viagens!",
          isUser: false,
          timestamp: new Date()
        };

        setMessages(prev => [...prev, botMessage]);
        setRecommendations(data);
      } else {
        console.log(response);
        throw new Error('Failed to get recommendations');
      }
    } catch (error) {
      console.log(error.response)
      const errorMessage = {
        id: Date.now() + 1,
        text: "Desculpe, ocorreu um erro ao buscar recomendações. Tente novamente.",
        isUser: false,
        timestamp: new Date()
      };
      setMessages(prev => [...prev, errorMessage]);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToHistory = (destination) => {
    setSelectedDestination(destination);
    setModalOpen(true);
  };
  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      
      handleSendMessage(e);
    }
  };

  const handleSubmitHistory = async (formData) => {
    if (!selectedDestination) return;

    try {
      const token = localStorage.getItem("token");
      const payload = {
        city: selectedDestination.city,
        country: selectedDestination.country,
        isFavorite: formData.isFavorite,
        hasVisited: formData.hasVisited,
        lastVisitedDate: formData.lastVisitedDate || null
      };

      const response = await fetch(`/users/${user.id}/history`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        alert('Destino adicionado ao histórico com sucesso!');
        setModalOpen(false);
        setSelectedDestination(null);
      } else {
        throw new Error('Failed to add to history');
      }
    } catch (error) {
      alert('Erro ao adicionar destino ao histórico.');
    }
  };

  if (authLoading) {
    return (
      <HomeScreen>
        <div style={{ 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center', 
          height: '100vh',
          color: 'var(--text-color)',
          fontSize: '1.2rem'
        }}>
          Carregando...
        </div>
      </HomeScreen>
    );
  }

  if (!authenticated) {
    return (
      <HomeScreen>
        <div style={{ 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center', 
          height: '100vh',
          color: 'var(--text-color)',
          fontSize: '1.2rem'
        }}>
          Por favor, faça login para ver as recomendações.
        </div>
      </HomeScreen>
    );
  }

  const userName = user?.name || user?.nome || "Viajante";

  return (
    <HomeScreen>
      <HomeHeader>
        <HeaderContent>
          <h1>Olá, {userName}!</h1>
          <h2>Para onde vamos viajar hoje?</h2>
        </HeaderContent>
      </HomeHeader>

      <Container>
        <ChatContainer>
          <MessagesContainer>
            {messages.length === 0 && (
              <Message isUser={false}>
                <MessageBubble isUser={false}>
                  Olá! Sou seu assistente de viagens. Pergunte-me sobre destinos ou peça recomendações personalizadas!
                </MessageBubble>
              </Message>
            )}
            {messages.map((message) => (
              <Message key={message.id} isUser={message.isUser}>
                <MessageBubble isUser={message.isUser}>
                  {message.text}
                </MessageBubble>
              </Message>
            ))}
            {isLoading && (
              <Message isUser={false}>
                <MessageBubble isUser={false}>
                  <LoadingDots>
                    <Dot />
                    <Dot />
                    <Dot />
                  </LoadingDots>
                </MessageBubble>
              </Message>
            )}
            <div ref={messagesEndRef} />
          </MessagesContainer>
          <InputContainer>
            <form onSubmit={handleSendMessage} style={{ display: 'flex', width: '100%', alignItems: 'flex-end' }}>
              
              <TextArea
                rows="1"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                onKeyDown={handleKeyDown}
                placeholder="Pergunte sobre destinos para sua próxima viagem..."
                disabled={isLoading}
                onInput={(e) => {
                  e.target.style.height = 'auto';
                  e.target.style.height = `${e.target.scrollHeight}px`;
                }}
              />
              
              <SendButton type="submit" disabled={isLoading}>
                <FaPaperPlane />
              </SendButton>
            </form>
          </InputContainer>
        </ChatContainer>

        {recommendations.length > 0 && (
          <RecommendationsSection>
            <SectionTitle>Destinos Recomendados para Você</SectionTitle>
            <CardsContainer>
              {recommendations.map((destination, index) => (
                <RecommendationCard key={index}>
                  <CardInfo>
                    <CardHeader>
                      <LocationInfo>
                        <CityName>{destination.city}</CityName>
                        <CountryName>{destination.country}</CountryName>
                      </LocationInfo>
                      <AddButton onClick={() => handleAddToHistory(destination)}>
                        <FaPlus />
                        Adicionar
                      </AddButton>
                    </CardHeader>
                    
                    <div>
                      <h4 style={{ margin: '1rem 0 0.5rem 0', color: 'var(--text-color)' }}>Locais para visitar:</h4>
                      <PlacesList>
                        {destination.mustVisitPlaces.map((place, placeIndex) => (
                          <PlaceItem key={placeIndex}>
                            <PlaceIcon />
                            <PlaceInfo>
                              <PlaceName>{place.name}</PlaceName>
                              <Coordinates>
                                Lat: {place.latitude}, Lng: {place.longitude}
                              </Coordinates>
                            </PlaceInfo>
                          </PlaceItem>
                        ))}
                      </PlacesList>
                    </div>
                  </CardInfo>
                </RecommendationCard>
              ))}
            </CardsContainer>
          </RecommendationsSection>
        )}
      </Container>

      <AppFooter>
        <FooterIcon onClick={() => navigate('/dashboard')}>
          <FaHome />
          <p>Início</p>
        </FooterIcon>
        <FooterIcon active>
          <FaSuitcase />
          <p>Recomendações</p>
        </FooterIcon>
        <FooterIcon>
          <FaHeart />
          <p>Favoritos</p>
        </FooterIcon>
        <FooterIcon>
          <FaCog />
          <p>Ajustes</p>
        </FooterIcon>
      </AppFooter>

      {isModalOpen && selectedDestination && (
        <AddDestinationModal
          destination={selectedDestination}
          onClose={() => setModalOpen(false)}
          onSubmit={handleSubmitHistory}
        />
      )}
    </HomeScreen>
  );
};

// Modal Component (same as before, but with updated styling)
const AddDestinationModal = ({ destination, onClose, onSubmit }) => {
  const [formData, setFormData] = useState({
    hasVisited: false,
    isFavorite: false,
    lastVisitedDate: ""
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <ModalOverlay onClick={onClose}>
      <Modal onClick={(e) => e.stopPropagation()}>
        <ModalTitle>
          Adicionar {destination.city}, {destination.country} ao histórico
        </ModalTitle>
        
        <Form onSubmit={handleSubmit}>
          <CheckboxGroup>
            <CheckboxLabel>
              <Checkbox
                type="checkbox"
                checked={formData.hasVisited}
                onChange={(e) => setFormData(prev => ({
                  ...prev,
                  hasVisited: e.target.checked
                }))}
              />
              Já visitei este local
            </CheckboxLabel>
            
            <CheckboxLabel>
              <Checkbox
                type="checkbox"
                checked={formData.isFavorite}
                onChange={(e) => setFormData(prev => ({
                  ...prev,
                  isFavorite: e.target.checked
                }))}
              />
              <FaStar color="var(--secondary-color)" />
              Favorito
            </CheckboxLabel>
          </CheckboxGroup>

          {formData.hasVisited && (
            <FormGroup>
              <Label>Data da última visita:</Label>
              <input
                type="date"
                value={formData.lastVisitedDate}
                onChange={(e) => setFormData(prev => ({
                  ...prev,
                  lastVisitedDate: e.target.value
                }))}
                style={{
                  padding: '0.75rem',
                  border: '1px solid var(--light-gray)',
                  borderRadius: '50px',
                  background: 'var(--light-gray)'
                }}
              />
            </FormGroup>
          )}

          <ModalButtons>
            <Button type="button" onClick={onClose}>
              Cancelar
            </Button>
            <Button primary type="submit">
              Adicionar ao Histórico
            </Button>
          </ModalButtons>
        </Form>
      </Modal>
    </ModalOverlay>
  );
};

export default RecommendationChatPage;
