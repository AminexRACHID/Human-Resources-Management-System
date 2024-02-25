import {Component, ElementRef, HostBinding, OnInit, Renderer2} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from '@angular/router'

@Component({
  selector: 'app-homestartassets',
  templateUrl: './homestartassets.component.html',
  styleUrls: ['./homestartassets.component.css',]
})
export class HomestartassetsComponent implements OnInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;
  loading: boolean = true;

  private scriptUrls: string[] = [
    "https://cdn.jsdelivr.net/npm/emailjs-com@2/dist/email.min.js",
    "../../../assets/vendor/swiper/swiper-bundle.min.js",
    "../../../assets/vendor/glightbox/js/glightbox.min.js",
    "../../../assets/vendor/purecounter/purecounter_vanilla.js",
    "../../../assets/vendor/aos/aos.js",
    "../../../assets/vendor/isotope-layout/isotope.pkgd.min.js",
    "../../../assets/js/main.js",
    "../../../assets/vendor/php-email-form/validate.js",
  ];
  private styleUrls: string[] = [
    "https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i",
    "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css",
    "../../../assets/vendor/aos/aos.css",
    "../../../assets/vendor/swiper/swiper-bundle.min.css",
    "../../../assets/vendor/glightbox/css/glightbox.min.css",
    "../../../assets/vendor/remixicon/remixicon.css",
    "../../../assets/vendor/bootstrap/css/bootstrap.min.css",
    "../../../assets/vendor/bootstrap-icons/bootstrap-icons.css",
    "../../../assets/css/style.css"
  ];

  constructor(private renderer: Renderer2, private el: ElementRef, private router: Router) {}

  ngOnInit(): void {
    // Subscribe to NavigationStart event
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        // Navigation has started, show the loading indicator
        this.loading = true;
      } else if (event instanceof NavigationEnd) {
        // Navigation has ended, hide the loading indicator
        this.loading = false;
      }
    });

    // Sequentially load scripts and styles
    this.loadScripts(this.scriptUrls)
      .then(() => this.loadStyles(this.styleUrls))
      .catch(error => console.error('Error loading scripts or styles:', error));
  }

  private loadScripts(scriptUrls: string[]): Promise<void> {
    return scriptUrls.reduce((previousPromise, scriptUrl) => {
      return previousPromise.then(() => this.loadScript(scriptUrl));
    }, Promise.resolve());
  }
  private handleScriptError(error: Event, reject: (reason?: any) => void): void {
    reject(error);
  }
  private loadScript(scriptUrl: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const script = this.renderer.createElement('script');
      script.type = 'text/javascript';
      script.src = scriptUrl;
      script.onload = () => resolve();
      script.onerror = (error: Event) => this.handleScriptError(error, reject);

      this.renderer.appendChild(this.el.nativeElement, script);
    });
  }

  private loadStyles(styleUrls: string[]): Promise<void> {
    return styleUrls.reduce((previousPromise, styleUrl) => {
      return previousPromise.then(() => this.loadStyle(styleUrl));
    }, Promise.resolve());
  }
  private handleStyleError(error: Event, reject: (reason?: any) => void): void {
    reject(error);
  }
  private loadStyle(styleUrl: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const style = this.renderer.createElement('link');
      style.rel = 'stylesheet';
      style.type = 'text/css';
      style.href = styleUrl;
      style.onload = () => resolve();
      style.onerror = (error: any) => this.handleStyleError(error, reject);

      this.renderer.appendChild(this.el.nativeElement, style);
    });
  }

}
