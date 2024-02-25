import {Component, ElementRef, HostBinding, OnInit, Renderer2} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from '@angular/router'
@Component({
  selector: 'app-adminassets',
  templateUrl: './adminassets.component.html',
  styleUrl: './adminassets.component.css'
})
export class AdminassetsComponent implements OnInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;
  loading: boolean = true;

  private scriptUrls: string[] = [
    // '../../../assets/admin-assets/assets/js/feather-icons/feather.min.js',
    '../../../assets/admin-assets/assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js',
    '../../../assets/admin-assets/assets/js/app.js',
    // '../../../assets/admin-assets/assets/vendors/simple-datatables/simple-datatables.js',
    // "https://code.jquery.com/jquery-3.6.4.min.js",
    // "https://cdn.datatables.net/2.0.0/js/dataTables.min.js",
    //  '../../../assets/admin-assets/assets/js/vendors.js',
    '../../../assets/admin-assets/assets/js/main.js',
  ];
  private styleUrls: string[] = [
    '../../../assets/admin-assets/assets/css/bootstrap.css',
    '../../../assets/admin-assets/assets/vendors/simple-datatables/style.css',
    '../../../assets/admin-assets/assets/vendors/perfect-scrollbar/perfect-scrollbar.css',
    // "https://cdn.datatables.net/2.0.0/css/dataTables.dataTables.min.css",
    '../../../assets/admin-assets/assets/css/app.css'
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
