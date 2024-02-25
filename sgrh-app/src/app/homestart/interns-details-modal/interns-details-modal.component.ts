import {Component, ElementRef, HostBinding, OnInit, Renderer2} from '@angular/core';

@Component({
  selector: 'app-interns-details-modal',
  templateUrl: './interns-details-modal.component.html',
  styleUrls: [
    './interns-details-modal.component.css',
    '../../../assets/interns/css/owl.carousel.min.css',
    '../../../assets/interns/css/flaticon.css',
    '../../../assets/interns/css/price_rangs.css',
    '../../../assets/interns/css/slicknav.css',
    '../../../assets/interns/css/animate.min.css',
    '../../../assets/interns/css/magnific-popup.css',
    '../../../assets/interns/css/fontawesome-all.min.css',
    '../../../assets/interns/css/themify-icons.css',
    '../../../assets/interns/css/slick.css',
    '../../../assets/interns/css/nice-select.css',
    '../../../assets/interns/css/style.css'
  ],
})
export class InternsDetailsModalComponent implements OnInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;
  private styleUrls: string[] = [
    '../../../assets/interns/css/owl.carousel.min.css',
    '../../../assets/interns/css/flaticon.css',
    '../../../assets/interns/css/price_rangs.css',
    '../../../assets/interns/css/slicknav.css',
    '../../../assets/interns/css/animate.min.css',
    '../../../assets/interns/css/magnific-popup.css',
    '../../../assets/interns/css/fontawesome-all.min.css',
    '../../../assets/interns/css/themify-icons.css',
    '../../../assets/interns/css/slick.css',
    '../../../assets/interns/css/nice-select.css',
    '../../../assets/interns/css/style.css'
  ];

  constructor(private renderer: Renderer2, private el: ElementRef) {}

  ngOnInit(): void {

    // Dynamically load styles for this component
    this.loadStyles(this.styleUrls);
  }



  private loadStyles(styleUrls: string[]): void {
    styleUrls.forEach((styleUrl) => {
      const style = this.renderer.createElement('link');
      style.rel = 'stylesheet';
      style.type = 'text/css';
      style.href = styleUrl;
      this.renderer.appendChild(this.el.nativeElement, style);
    });
  }

}
