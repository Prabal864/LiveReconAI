import { useState } from 'react';

const LandingPage = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const features = [
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
        </svg>
      ),
      title: 'Visualize Transactions',
      description: 'Get beautiful, interactive charts and graphs of your financial data. Track spending patterns and identify trends with ease.',
    },
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
        </svg>
      ),
      title: 'Natural Language Q&A',
      description: 'Ask questions about your finances in plain English. Our AI understands context and provides intelligent, actionable insights.',
    },
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
        </svg>
      ),
      title: 'Consent-Based Security',
      description: 'Your data is protected with bank-grade security. All access is consent-based using the Setu Account Aggregator framework.',
    },
  ];

  return (
    <div className="min-h-screen bg-[#0a0a0f] text-white overflow-hidden">
      {/* Gradient background effects */}
      <div className="fixed inset-0 overflow-hidden pointer-events-none">
        <div className="absolute top-[-20%] left-[-10%] w-[500px] h-[500px] bg-purple-600/20 rounded-full blur-[120px]"></div>
        <div className="absolute top-[20%] right-[-10%] w-[400px] h-[400px] bg-blue-600/20 rounded-full blur-[120px]"></div>
        <div className="absolute bottom-[-10%] left-[30%] w-[600px] h-[400px] bg-cyan-600/15 rounded-full blur-[120px]"></div>
      </div>

      {/* Navigation */}
      <nav className="relative z-50 border-b border-white/10 backdrop-blur-xl bg-[#0a0a0f]/80">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            {/* Logo */}
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-gradient-to-br from-purple-500 to-cyan-500 rounded-lg flex items-center justify-center">
                <span className="text-white font-bold text-sm">LR</span>
              </div>
              <span className="text-xl font-bold bg-gradient-to-r from-white to-gray-400 bg-clip-text text-transparent">
                LiveReconAI
              </span>
            </div>

            {/* Desktop Navigation */}
            <div className="hidden md:flex items-center space-x-8">
              <a href="#features" className="text-gray-300 hover:text-white transition-colors">Features</a>
              <a href="#how-it-works" className="text-gray-300 hover:text-white transition-colors">How It Works</a>
              <a href="#security" className="text-gray-300 hover:text-white transition-colors">Security</a>
            </div>

            {/* CTA Buttons */}
            <div className="hidden md:flex items-center space-x-4">
              <button className="text-gray-300 hover:text-white transition-colors px-4 py-2">
                Sign In
              </button>
              <button className="bg-gradient-to-r from-purple-600 to-cyan-600 hover:from-purple-500 hover:to-cyan-500 text-white px-6 py-2 rounded-lg font-medium transition-all transform hover:scale-105 shadow-lg shadow-purple-500/25">
                Get Started
              </button>
            </div>

            {/* Mobile menu button */}
            <button 
              className="md:hidden text-gray-300 hover:text-white"
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                {mobileMenuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                )}
              </svg>
            </button>
          </div>
        </div>

        {/* Mobile menu */}
        {mobileMenuOpen && (
          <div className="md:hidden border-t border-white/10 bg-[#0a0a0f]/95 backdrop-blur-xl">
            <div className="px-4 py-4 space-y-3">
              <a href="#features" className="block text-gray-300 hover:text-white transition-colors py-2">Features</a>
              <a href="#how-it-works" className="block text-gray-300 hover:text-white transition-colors py-2">How It Works</a>
              <a href="#security" className="block text-gray-300 hover:text-white transition-colors py-2">Security</a>
              <div className="pt-4 space-y-3">
                <button className="w-full text-gray-300 hover:text-white transition-colors py-2 text-left">
                  Sign In
                </button>
                <button className="w-full bg-gradient-to-r from-purple-600 to-cyan-600 text-white px-6 py-2 rounded-lg font-medium">
                  Get Started
                </button>
              </div>
            </div>
          </div>
        )}
      </nav>

      {/* Hero Section */}
      <section className="relative z-10 pt-20 pb-32 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto text-center">
          {/* Badge */}
          <div className="inline-flex items-center px-4 py-2 rounded-full border border-purple-500/30 bg-purple-500/10 text-purple-300 text-sm mb-8">
            <span className="w-2 h-2 bg-green-400 rounded-full mr-2 animate-pulse"></span>
            Powered by Setu Account Aggregator
          </div>

          {/* Main Heading */}
          <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-bold mb-6 leading-tight">
            <span className="bg-gradient-to-r from-white via-purple-200 to-white bg-clip-text text-transparent">
              Financial Insights
            </span>
            <br />
            <span className="bg-gradient-to-r from-purple-400 via-cyan-400 to-purple-400 bg-clip-text text-transparent">
              Powered by AI
            </span>
          </h1>

          {/* Subtitle */}
          <p className="text-gray-400 text-lg sm:text-xl max-w-2xl mx-auto mb-10">
            Transform your financial data into actionable insights. Ask questions in natural language, 
            visualize trends, and make smarter decisions with AI-powered analytics.
          </p>

          {/* CTA Buttons */}
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-16">
            <button className="w-full sm:w-auto bg-gradient-to-r from-purple-600 to-cyan-600 hover:from-purple-500 hover:to-cyan-500 text-white px-8 py-4 rounded-xl font-semibold text-lg transition-all transform hover:scale-105 shadow-2xl shadow-purple-500/30">
              Start Free Trial
            </button>
            <button className="w-full sm:w-auto border border-white/20 hover:border-white/40 text-white px-8 py-4 rounded-xl font-semibold text-lg transition-all backdrop-blur-sm hover:bg-white/5">
              Watch Demo
            </button>
          </div>

          {/* Dashboard Mockup */}
          <div className="relative max-w-5xl mx-auto">
            {/* Glow effect behind mockup */}
            <div className="absolute inset-0 bg-gradient-to-r from-purple-600/30 via-cyan-600/30 to-purple-600/30 blur-3xl transform scale-95"></div>
            
            {/* Mockup container */}
            <div className="relative bg-[#12121a] border border-white/10 rounded-2xl p-2 shadow-2xl">
              {/* Browser bar */}
              <div className="flex items-center gap-2 px-4 py-3 border-b border-white/10">
                <div className="flex gap-2">
                  <div className="w-3 h-3 rounded-full bg-red-500/80"></div>
                  <div className="w-3 h-3 rounded-full bg-yellow-500/80"></div>
                  <div className="w-3 h-3 rounded-full bg-green-500/80"></div>
                </div>
                <div className="flex-1 mx-4">
                  <div className="bg-[#1a1a24] rounded-lg px-4 py-1.5 text-gray-500 text-sm max-w-md mx-auto">
                    app.livereconai.com/dashboard
                  </div>
                </div>
              </div>
              
              {/* Dashboard content */}
              <div className="p-6 bg-gradient-to-b from-[#12121a] to-[#0f0f15] rounded-b-xl">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
                  {/* Stat cards */}
                  <div className="bg-[#1a1a24] border border-white/5 rounded-xl p-4">
                    <p className="text-gray-500 text-sm mb-1">Total Balance</p>
                    <p className="text-2xl font-bold text-white">₹12,45,890</p>
                    <p className="text-green-400 text-sm mt-1">+12.5% this month</p>
                  </div>
                  <div className="bg-[#1a1a24] border border-white/5 rounded-xl p-4">
                    <p className="text-gray-500 text-sm mb-1">Monthly Spending</p>
                    <p className="text-2xl font-bold text-white">₹78,450</p>
                    <p className="text-red-400 text-sm mt-1">-8.3% vs last month</p>
                  </div>
                  <div className="bg-[#1a1a24] border border-white/5 rounded-xl p-4">
                    <p className="text-gray-500 text-sm mb-1">Savings Goal</p>
                    <p className="text-2xl font-bold text-white">72%</p>
                    <div className="w-full bg-gray-700 rounded-full h-2 mt-2">
                      <div className="bg-gradient-to-r from-purple-500 to-cyan-500 h-2 rounded-full" style={{width: '72%'}}></div>
                    </div>
                  </div>
                </div>
                
                {/* Chart placeholder */}
                <div className="bg-[#1a1a24] border border-white/5 rounded-xl p-4">
                  <div className="flex items-center justify-between mb-4">
                    <h3 className="text-white font-medium">Spending Overview</h3>
                    <div className="flex gap-2">
                      <span className="px-3 py-1 bg-purple-500/20 text-purple-300 rounded-lg text-sm">Weekly</span>
                      <span className="px-3 py-1 text-gray-500 rounded-lg text-sm">Monthly</span>
                    </div>
                  </div>
                  {/* Simplified chart bars */}
                  <div className="flex items-end justify-between h-32 gap-2">
                    {[60, 40, 80, 55, 90, 45, 70].map((height, i) => (
                      <div key={i} className="flex-1 bg-gradient-to-t from-purple-600 to-cyan-500 rounded-t-lg opacity-80" style={{height: `${height}%`}}></div>
                    ))}
                  </div>
                  <div className="flex justify-between mt-2 text-gray-500 text-xs">
                    <span>Mon</span>
                    <span>Tue</span>
                    <span>Wed</span>
                    <span>Thu</span>
                    <span>Fri</span>
                    <span>Sat</span>
                    <span>Sun</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="relative z-10 py-24 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-3xl sm:text-4xl font-bold mb-4">
              <span className="bg-gradient-to-r from-white to-gray-400 bg-clip-text text-transparent">
                Powerful Features
              </span>
            </h2>
            <p className="text-gray-400 text-lg max-w-2xl mx-auto">
              Everything you need to understand and optimize your financial health
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {features.map((feature, index) => (
              <div 
                key={index}
                className="group relative bg-[#12121a] border border-white/10 rounded-2xl p-8 hover:border-purple-500/50 transition-all duration-300"
              >
                {/* Hover glow effect */}
                <div className="absolute inset-0 bg-gradient-to-r from-purple-600/10 to-cyan-600/10 rounded-2xl opacity-0 group-hover:opacity-100 transition-opacity"></div>
                
                <div className="relative">
                  <div className="w-14 h-14 bg-gradient-to-br from-purple-600 to-cyan-600 rounded-xl flex items-center justify-center mb-6 shadow-lg shadow-purple-500/20">
                    {feature.icon}
                  </div>
                  <h3 className="text-xl font-semibold text-white mb-3">{feature.title}</h3>
                  <p className="text-gray-400 leading-relaxed">{feature.description}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* How It Works Section */}
      <section id="how-it-works" className="relative z-10 py-24 px-4 sm:px-6 lg:px-8 bg-[#08080c]">
        <div className="max-w-7xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-3xl sm:text-4xl font-bold mb-4">
              <span className="bg-gradient-to-r from-white to-gray-400 bg-clip-text text-transparent">
                How It Works
              </span>
            </h2>
            <p className="text-gray-400 text-lg max-w-2xl mx-auto">
              Get started in just a few simple steps
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            {[
              { step: '01', title: 'Connect Your Accounts', desc: 'Securely link your bank accounts through Setu AA' },
              { step: '02', title: 'Grant Consent', desc: 'Control exactly what data you share and for how long' },
              { step: '03', title: 'AI Analysis', desc: 'Our AI processes your data to generate insights' },
              { step: '04', title: 'Get Insights', desc: 'View dashboards and ask questions about your finances' },
            ].map((item, index) => (
              <div key={index} className="text-center">
                <div className="w-16 h-16 mx-auto mb-4 rounded-full bg-gradient-to-r from-purple-600/20 to-cyan-600/20 border border-purple-500/30 flex items-center justify-center">
                  <span className="text-2xl font-bold bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
                    {item.step}
                  </span>
                </div>
                <h3 className="text-lg font-semibold text-white mb-2">{item.title}</h3>
                <p className="text-gray-400 text-sm">{item.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* AI Q&A Section */}
      <section className="relative z-10 py-24 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
            <div>
              <h2 className="text-3xl sm:text-4xl font-bold mb-6">
                <span className="bg-gradient-to-r from-white to-gray-400 bg-clip-text text-transparent">
                  Ask Anything About
                </span>
                <br />
                <span className="bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
                  Your Finances
                </span>
              </h2>
              <p className="text-gray-400 text-lg mb-8">
                Our AI understands natural language, so you can ask questions like you would ask a financial advisor.
              </p>
              <div className="space-y-4">
                {[
                  'Where did I spend the most last month?',
                  'How much did I spend on food?',
                  'What are my recurring expenses?',
                  'Suggest areas where I can save money',
                ].map((question, index) => (
                  <div 
                    key={index}
                    className="flex items-center gap-3 p-4 bg-[#12121a] border border-white/10 rounded-xl hover:border-purple-500/30 transition-colors cursor-pointer"
                  >
                    <div className="w-8 h-8 rounded-full bg-purple-500/20 flex items-center justify-center flex-shrink-0">
                      <svg className="w-4 h-4 text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                    </div>
                    <span className="text-gray-300">{question}</span>
                  </div>
                ))}
              </div>
            </div>
            
            {/* Chat mockup */}
            <div className="relative">
              <div className="absolute inset-0 bg-gradient-to-r from-purple-600/20 to-cyan-600/20 blur-3xl"></div>
              <div className="relative bg-[#12121a] border border-white/10 rounded-2xl p-6">
                <div className="space-y-4">
                  {/* User message */}
                  <div className="flex justify-end">
                    <div className="bg-gradient-to-r from-purple-600 to-cyan-600 text-white px-4 py-3 rounded-2xl rounded-tr-md max-w-xs">
                      How much did I spend on food last month?
                    </div>
                  </div>
                  {/* AI response */}
                  <div className="flex gap-3">
                    <div className="w-8 h-8 rounded-full bg-gradient-to-r from-purple-500 to-cyan-500 flex-shrink-0 flex items-center justify-center">
                      <span className="text-white text-xs font-bold">AI</span>
                    </div>
                    <div className="bg-[#1a1a24] text-gray-300 px-4 py-3 rounded-2xl rounded-tl-md max-w-sm">
                      <p className="mb-2">Based on your transaction data, you spent <span className="text-purple-400 font-semibold">₹12,450</span> on food-related expenses last month.</p>
                      <p className="text-sm text-gray-500">This includes restaurants (₹7,200), groceries (₹4,100), and food delivery apps (₹1,150).</p>
                    </div>
                  </div>
                </div>
                {/* Input field */}
                <div className="mt-6 flex gap-2">
                  <input 
                    type="text" 
                    placeholder="Ask about your finances..."
                    className="flex-1 bg-[#1a1a24] border border-white/10 rounded-xl px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-purple-500/50"
                    readOnly
                  />
                  <button className="bg-gradient-to-r from-purple-600 to-cyan-600 text-white px-4 rounded-xl">
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

      {/* Security Section */}
      <section id="security" className="relative z-10 py-24 px-4 sm:px-6 lg:px-8 bg-[#08080c]">
        <div className="max-w-7xl mx-auto text-center">
          <div className="inline-flex items-center px-4 py-2 rounded-full border border-green-500/30 bg-green-500/10 text-green-300 text-sm mb-8">
            <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
            </svg>
            Bank-Grade Security
          </div>
          
          <h2 className="text-3xl sm:text-4xl font-bold mb-6">
            <span className="bg-gradient-to-r from-white to-gray-400 bg-clip-text text-transparent">
              Your Data, Your Control
            </span>
          </h2>
          <p className="text-gray-400 text-lg max-w-2xl mx-auto mb-12">
            Built on the RBI-approved Account Aggregator framework. All data access is consent-based, 
            time-bound, and fully encrypted.
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {[
              { icon: '🔐', title: 'End-to-End Encryption', desc: 'Your data is encrypted at rest and in transit' },
              { icon: '✅', title: 'Consent-Based Access', desc: 'You control what data is shared and for how long' },
              { icon: '🏛️', title: 'RBI Approved', desc: 'Built on the official Account Aggregator framework' },
              { icon: '🚫', title: 'No Data Storage', desc: 'We never store your raw financial data' },
            ].map((item, index) => (
              <div key={index} className="bg-[#12121a] border border-white/10 rounded-xl p-6">
                <div className="text-3xl mb-4">{item.icon}</div>
                <h3 className="text-white font-semibold mb-2">{item.title}</h3>
                <p className="text-gray-400 text-sm">{item.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="relative z-10 py-24 px-4 sm:px-6 lg:px-8">
        <div className="max-w-4xl mx-auto text-center">
          <div className="relative bg-gradient-to-r from-purple-900/50 to-cyan-900/50 border border-purple-500/30 rounded-3xl p-12">
            {/* Glow effect */}
            <div className="absolute inset-0 bg-gradient-to-r from-purple-600/20 to-cyan-600/20 blur-3xl -z-10"></div>
            
            <h2 className="text-3xl sm:text-4xl font-bold mb-4">
              <span className="bg-gradient-to-r from-white to-gray-200 bg-clip-text text-transparent">
                Ready to Transform Your
              </span>
              <br />
              <span className="bg-gradient-to-r from-purple-400 to-cyan-400 bg-clip-text text-transparent">
                Financial Insights?
              </span>
            </h2>
            <p className="text-gray-400 text-lg mb-8">
              Join thousands of users who are already making smarter financial decisions with LiveReconAI.
            </p>
            <button className="bg-gradient-to-r from-purple-600 to-cyan-600 hover:from-purple-500 hover:to-cyan-500 text-white px-10 py-4 rounded-xl font-semibold text-lg transition-all transform hover:scale-105 shadow-2xl shadow-purple-500/30">
              Get Started for Free
            </button>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="relative z-10 border-t border-white/10 py-12 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
            <div>
              <div className="flex items-center space-x-2 mb-4">
                <div className="w-8 h-8 bg-gradient-to-br from-purple-500 to-cyan-500 rounded-lg flex items-center justify-center">
                  <span className="text-white font-bold text-sm">LR</span>
                </div>
                <span className="text-xl font-bold text-white">LiveReconAI</span>
              </div>
              <p className="text-gray-400 text-sm">
                AI-powered financial insights using secure account aggregation.
              </p>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">Product</h4>
              <ul className="space-y-2 text-gray-400 text-sm">
                <li><a href="#" className="hover:text-white transition-colors">Features</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Pricing</a></li>
                <li><a href="#" className="hover:text-white transition-colors">API</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">Company</h4>
              <ul className="space-y-2 text-gray-400 text-sm">
                <li><a href="#" className="hover:text-white transition-colors">About</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Blog</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Careers</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">Legal</h4>
              <ul className="space-y-2 text-gray-400 text-sm">
                <li><a href="#" className="hover:text-white transition-colors">Privacy</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Terms</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Security</a></li>
              </ul>
            </div>
          </div>
          <div className="border-t border-white/10 pt-8 text-center text-gray-500 text-sm">
            <p>© 2024 LiveReconAI. All rights reserved. Built with ❤️ by Prabal Pratap Singh</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default LandingPage;
