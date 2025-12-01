import { useState } from 'react';

const ChatDemo = () => {
  const [messages] = useState([
    { type: 'user', text: 'How much did I spend on food last month?' },
    { type: 'ai', text: 'Based on your transactions, you spent ₹12,450 on food and dining last month. This includes ₹8,200 on restaurants and ₹4,250 on groceries. This is 15% higher than your average monthly food spending.' },
    { type: 'user', text: 'Which restaurant did I visit most?' },
    { type: 'ai', text: 'You visited "Cafe Coffee Day" 8 times last month, spending a total of ₹2,400. Your second most frequent was "Domino\'s Pizza" with 5 visits totaling ₹1,850.' },
  ]);

  return (
    <section id="demo" className="py-20 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold text-white mb-4">
            Your{" "}
            <span className="bg-gradient-to-r from-purple-500 to-cyan-400 bg-clip-text text-transparent">
              financial ChatGPT
            </span>
          </h2>
          <p className="text-gray-400 text-lg max-w-2xl mx-auto">
            Ask anything about your finances and get instant, intelligent answers.
          </p>
        </div>
        
        <div className="max-w-3xl mx-auto">
          <div className="bg-gradient-to-b from-gray-800/50 to-gray-900/50 border border-gray-700/50 rounded-2xl overflow-hidden">
            {/* Chat header */}
            <div className="px-6 py-4 border-b border-gray-700/50 flex items-center gap-3">
              <div className="w-3 h-3 rounded-full bg-red-500"></div>
              <div className="w-3 h-3 rounded-full bg-yellow-500"></div>
              <div className="w-3 h-3 rounded-full bg-green-500"></div>
              <span className="ml-4 text-gray-400 text-sm">LiveReconAI Chat</span>
            </div>
            
            {/* Chat messages */}
            <div className="p-6 space-y-6 max-h-96 overflow-y-auto">
              {messages.map((message, index) => (
                <div
                  key={index}
                  className={`flex ${message.type === 'user' ? 'justify-end' : 'justify-start'}`}
                >
                  <div
                    className={`max-w-[80%] rounded-2xl px-4 py-3 ${
                      message.type === 'user'
                        ? 'bg-gradient-to-r from-purple-600 to-cyan-500 text-white'
                        : 'bg-gray-700/50 text-gray-100'
                    }`}
                  >
                    {message.type === 'ai' && (
                      <div className="flex items-center gap-2 mb-2">
                        <div className="w-5 h-5 rounded-full bg-gradient-to-r from-purple-500 to-cyan-400 flex items-center justify-center">
                          <svg className="w-3 h-3 text-white" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
                          </svg>
                        </div>
                        <span className="text-xs text-purple-400 font-medium">LiveReconAI</span>
                      </div>
                    )}
                    <p className="text-sm leading-relaxed">{message.text}</p>
                  </div>
                </div>
              ))}
            </div>
            
            {/* Chat input */}
            <div className="px-6 py-4 border-t border-gray-700/50">
              <div className="flex items-center gap-3">
                <input
                  type="text"
                  placeholder="Ask about your finances..."
                  className="flex-1 bg-gray-800/50 border border-gray-700 rounded-lg px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-purple-500 transition-colors"
                  disabled
                />
                <button className="bg-gradient-to-r from-purple-600 to-cyan-500 text-white p-3 rounded-lg hover:opacity-90 transition-opacity">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ChatDemo;
