import React from 'react';
import { ArrowRight, Shield, BarChart2, MessageSquare } from 'lucide-react';

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-black text-white font-sans selection:bg-purple-500 selection:text-white overflow-x-hidden">
      
      {/* Navbar */}
      <nav className="flex items-center justify-between px-6 py-6 max-w-7xl mx-auto">
        <div className="text-2xl font-bold tracking-tighter flex items-center gap-2">
          <div className="w-3 h-3 rounded-full bg-purple-500"></div>
          LiveReconAI
        </div>
        <div className="hidden md:flex gap-8 text-sm text-gray-400">
          <a href="#" className="hover:text-white transition-colors">Home</a>
          <a href="#" className="hover:text-white transition-colors">Features</a>
          <a href="#" className="hover:text-white transition-colors">Solution</a>
          <a href="#" className="hover:text-white transition-colors">About Us</a>
        </div>
        <div className="flex items-center gap-4">
          <a href="#" className="text-sm font-medium hover:text-purple-400">Login</a>
          <button className="bg-white text-black px-5 py-2 rounded-full text-sm font-bold hover:bg-gray-200 transition-transform hover:scale-105">
            Get Started
          </button>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="relative pt-20 pb-32 px-6 max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
        
        {/* Background Glow Effects */}
        <div className="absolute top-0 left-1/2 -translate-x-1/2 w-[500px] h-[500px] bg-purple-900/30 rounded-full blur-[120px] -z-10"></div>
        
        <div className="space-y-8">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-gray-800 bg-gray-900/50 text-xs text-gray-300">
            <span className="w-2 h-2 rounded-full bg-green-500"></span>
            Powered by Setu AA & OpenAI
          </div>
          
          <h1 className="text-5xl md:text-7xl font-bold leading-tight bg-clip-text text-transparent bg-gradient-to-b from-white to-gray-500">
            AI-Driven Insights for <br />
            <span className="text-white">Your Finance.</span>
          </h1>
          
          <p className="text-gray-400 text-lg max-w-lg leading-relaxed">
            Meet your finance copilot. We fuse Setu Account Aggregator with AI to turn raw financial data into clear, actionable insights. Just ask, "Where did I spend the most last month?"
          </p>
          
          <div className="flex flex-wrap gap-4 pt-4">
             <button className="bg-white text-black px-8 py-4 rounded-full font-bold flex items-center gap-2 hover:bg-gray-100 transition-all">
              Get Started <ArrowRight size={18} />
            </button>
             <button className="px-8 py-4 rounded-full font-bold border border-gray-700 hover:bg-gray-900 transition-all">
              View Demo
            </button>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-3 gap-8 pt-12 border-t border-gray-900 mt-12">
            <div>
              <h3 className="text-2xl font-bold">380+</h3>
              <p className="text-xs text-gray-500 uppercase tracking-wider mt-1">Active Users</p>
            </div>
            <div>
              <h3 className="text-2xl font-bold">100%</h3>
              <p className="text-xs text-gray-500 uppercase tracking-wider mt-1">Secure Consent</p>
            </div>
            <div>
              <h3 className="text-2xl font-bold">$230M+</h3>
              <p className="text-xs text-gray-500 uppercase tracking-wider mt-1">Transactions Analyzed</p>
            </div>
          </div>
        </div>

        {/* Abstract Visual / Dashboard Mockup */}
        <div className="relative">
          <div className="relative z-10 bg-gradient-to-br from-gray-900 to-black border border-gray-800 rounded-3xl p-6 shadow-2xl transform rotate-2 hover:rotate-0 transition-transform duration-500">
            <div className="flex items-center justify-between mb-6">
                <div className="flex gap-2">
                    <div className="w-3 h-3 rounded-full bg-red-500"></div>
                    <div className="w-3 h-3 rounded-full bg-yellow-500"></div>
                    <div className="w-3 h-3 rounded-full bg-green-500"></div>
                </div>
                <div className="text-xs text-gray-500">AI Analysis</div>
            </div>
            {/* Chat Interface Mock */}
            <div className="space-y-4">
                <div className="flex gap-3">
                    <div className="w-8 h-8 rounded-full bg-purple-600 flex items-center justify-center text-xs">AI</div>
                    <div className="bg-gray-800 rounded-r-xl rounded-bl-xl p-3 text-sm text-gray-300">
                        Based on your transactions, you spent <strong>₹12,400</strong> on dining out this month. That's 15% higher than average.
                    </div>
                </div>
                <div className="flex gap-3 flex-row-reverse">
                     <div className="w-8 h-8 rounded-full bg-gray-600 flex items-center justify-center text-xs">You</div>
                    <div className="bg-purple-900/30 border border-purple-500/30 rounded-l-xl rounded-br-xl p-3 text-sm text-white">
                        How can I save more next month?
                    </div>
                </div>
            </div>
             {/* Chart Mock */}
             <div className="mt-6 h-32 flex items-end gap-2 px-4 pb-2 border-b border-l border-gray-800">
                {[40, 65, 45, 90, 30, 70, 55].map((h, i) => (
                    <div key={i} className="flex-1 bg-purple-600/50 hover:bg-purple-500 transition-colors rounded-t-sm" style={{ height: `${h}%` }}></div>
                ))}
             </div>
          </div>
          {/* Decorative Elements */}
          <div className="absolute -bottom-10 -right-10 w-64 h-64 bg-blue-600/20 rounded-full blur-[80px]"></div>
        </div>
      </section>

      {/* Features Grid */}
      <section className="py-24 px-6 max-w-7xl mx-auto">
        <div className="text-center mb-16">
            <div className="inline-block px-3 py-1 mb-4 text-xs font-semibold tracking-wider text-purple-400 uppercase bg-purple-900/20 rounded-full">
                Explore Features
            </div>
            <h2 className="text-3xl md:text-4xl font-bold mb-4">Effortlessly customize <br/> for your unique goals.</h2>
        </div>

        <div className="grid md:grid-cols-3 gap-6">
            <FeatureCard 
                icon={<BarChart2 className="text-purple-400" />}
                title="Visualize Transactions"
                desc="Turn messy bank statements into beautiful, interactive charts and spending breakdowns instantly."
            />
             <FeatureCard 
                icon={<MessageSquare className="text-blue-400" />}
                title="Natural Language Q&A"
                desc="Ask questions like 'How much did I spend on Uber?' and get answers derived from real banking data."
            />
             <FeatureCard 
                icon={<Shield className="text-green-400" />}
                title="Consent-Based Security"
                desc="Your data is fetched only after explicit approval via Setu Account Aggregator. Safe, secure, and compliant."
            />
        </div>
      </section>

    </div>
  );
};

const FeatureCard = ({ icon, title, desc }) => (
    <div className="group p-8 rounded-3xl bg-[#0B0B0B] border border-gray-900 hover:border-gray-700 transition-colors">
        <div className="w-12 h-12 rounded-2xl bg-gray-900 flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
            {icon}
        </div>
        <h3 className="text-xl font-bold mb-3">{title}</h3>
        <p className="text-gray-400 text-sm leading-relaxed">
            {desc}
        </p>
    </div>
);

export default LandingPage;
