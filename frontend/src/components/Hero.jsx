const Hero = () => {
  return (
    <section className="pt-32 pb-20 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto text-center">
        <div className="mb-6">
          <span className="inline-block px-4 py-1.5 bg-purple-500/10 border border-purple-500/20 rounded-full text-purple-400 text-sm font-medium">
            Powered by Setu Account Aggregator + AI
          </span>
        </div>
        <h1 className="text-5xl sm:text-6xl lg:text-7xl font-bold mb-6">
          <span className="text-white">Meet your </span>
          <span className="bg-gradient-to-r from-purple-500 via-pink-500 to-cyan-400 bg-clip-text text-transparent">
            finance copilot
          </span>
        </h1>
        <p className="text-xl text-gray-400 max-w-3xl mx-auto mb-10">
          Turn raw financial data into clear, actionable insights. Consent-based access, 
          visualization-ready transactions, and natural-language Q&A—all in one platform.
        </p>
        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <button className="bg-gradient-to-r from-purple-600 to-cyan-500 text-white px-8 py-4 rounded-lg font-semibold text-lg hover:opacity-90 transition-opacity shadow-lg shadow-purple-500/25">
            Get Started
          </button>
          <button className="border border-gray-600 text-white px-8 py-4 rounded-lg font-semibold text-lg hover:bg-gray-800 transition-colors">
            Watch Demo
          </button>
        </div>
        
        {/* Decorative gradient orbs */}
        <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-purple-600/20 rounded-full blur-3xl -z-10"></div>
        <div className="absolute top-1/3 right-1/4 w-96 h-96 bg-cyan-600/20 rounded-full blur-3xl -z-10"></div>
      </div>
    </section>
  );
};

export default Hero;
